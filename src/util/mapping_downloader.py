#   Copyright (c) 2020. TeamMoeg
#
#   This file is part of Energy Level Transition.
#
#   Energy Level Transition is free software: you can redistribute it and/or modify
#   it under the terms of the GNU General Public License as published by
#   the Free Software Foundation, version 3.
#
#   Energy Level Transition is distributed in the hope that it will be useful,
#   but WITHOUT ANY WARRANTY; without even the implied warranty of
#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#   GNU General Public License for more details.
#
#   You should have received a copy of the GNU General Public License
#   along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.

# Simple one-time downloader for various minecraft mappings sources and files
# Caches all downloaded files locally

import datetime
import io
import json
import os
import urllib.error
import urllib.request
import zipfile

from typing import Tuple, Optional, Any, Dict, List

FABRIC_YARN_V2_META_URL = 'https://meta.fabricmc.net/v2/versions/yarn'
FABRIC_YARN_V2_URL = 'https://maven.fabricmc.net/net/fabricmc/yarn/%s+build.%s/yarn-%s+build.%s-v2.jar'
FABRIC_INTERMEDIARY_URL = 'https://raw.githubusercontent.com/FabricMC/intermediary/master/mappings//%s.tiny'
MCP_BOT_MCP_MAVEN_URL = 'http://export.mcpbot.bspk.rs/mcp_snapshot/%s-%s/mcp_snapshot-%s-%s.zip'
FORGE_MCP_MAVEN_URL = 'https://files.minecraftforge.net/maven/de/oceanlabs/mcp/mcp_snapshot/%s-%s/mcp_snapshot-%s-%s.zip'
GIGAHERTZ_MCP_MAVEN_URL = 'https://www.dogforce-games.com/maven/de/oceanlabs/mcp/mcp_snapshot/%s-%s/mcp_snapshot-%s-%s.zip'
TTERRAG_MCP_MAVEN_URL = 'https://maven.tterrag.com/de/oceanlabs/mcp/mcp_snapshot/%s-%s-%s/mcp_snapshot-%s-%s-%s.zip'
FORGE_MCP_CONFIG_URL = 'https://files.minecraftforge.net/maven/de/oceanlabs/mcp/mcp_config/%s/mcp_config-%s.zip'
OFFICIAL_MANIFEST_URL = 'https://launchermeta.mojang.com/mc/game/version_manifest.json'
FORGE_MCP_SPREADSHEET_URL = 'https://docs.google.com/spreadsheets/u/0/d/14knNUYjYkKkGpW9VTyjtlhaCTUsPWRJ91GLOFX2d23Q/export?gid=1460654099&format=csv'

# API link to get the spreadsheet url: https://spreadsheets.google.com/feeds/worksheets/{fileId}/private/full

DEFAULT_MCP_MAVENS = [GIGAHERTZ_MCP_MAVEN_URL, FORGE_MCP_MAVEN_URL, MCP_BOT_MCP_MAVEN_URL]

CACHE_ROOT = '../build/'
FABRIC_YARN_V2_CACHE = 'yarn_v2-%s+build.%s.tiny'
FABRIC_INTERMEDIARY_CACHE = 'yarn_intermediary-%s.tiny'
MCP_CACHE = 'mcp_snapshot-%s-%s'
MCP_CONFIG_CACHE = 'mcpconfig-%s'
OFFICIAL_MANIFEST_CACHE = 'official-manifest.json'
OFFICIAL_VERSION_MANIFEST_CACHE = 'official-manifest-%s.json'
OFFICIAL_MAPPING_CACHE = 'official-%s'
FORGE_SPREADSHEET_CACHE = 'forge-mms-spreadsheet-%s.csv'
CORRECTIONS_CACHE = 'corrections-%s.json'


def get_cache_root() -> str:
    global CACHE_ROOT
    return CACHE_ROOT


def set_cache_root(cache: str):
    global CACHE_ROOT
    CACHE_ROOT = cache
    # Verify that the cache location exists
    if not os.path.isdir(CACHE_ROOT):
        os.makedirs(CACHE_ROOT)


set_cache_root(CACHE_ROOT)


def load_yarn_v2(mc_version: str, yarn_version: Optional[str] = None) -> str:
    if yarn_version is None:
        # find the newest build
        yarn_mappings = sorted([f for f in os.listdir(CACHE_ROOT) if os.path.isfile(CACHE_ROOT + f) and f.startswith('yarn_v2-%s' % mc_version)])
        if yarn_mappings:
            with open(CACHE_ROOT + yarn_mappings[-1]) as f:
                return f.read()
    else:
        # find exact match
        path = CACHE_ROOT + FABRIC_YARN_V2_CACHE % (mc_version, yarn_version)
        if os.path.isfile(path):
            with open(path) as f:
                return f.read()

    # fetch yarn metadata in order to determine available build numbers
    with urllib.request.urlopen(FABRIC_YARN_V2_META_URL) as request:
        versions = json.loads(request.read().decode('utf-8'))

    # filter versions based on passed in mc and yarn version numbers
    versions = [v for v in versions if v['gameVersion'] == mc_version and (yarn_version is None or v['build'] == yarn_version)]
    if versions is None:
        raise RuntimeError('Unable to find a version matching %s and %s' % (mc_version, str(yarn_version)))
    if yarn_version is None:
        versions.sort(key=lambda k: -k['build'])
        yarn_version = versions[0]['build']

    # download specified yarn build
    url = FABRIC_YARN_V2_URL % (mc_version, yarn_version, mc_version, yarn_version)
    with urllib.request.urlopen(url) as request:
        tiny_jar = request.read()
    with io.BytesIO(tiny_jar) as fio:
        with zipfile.ZipFile(fio, 'r') as tiny_zip:
            with tiny_zip.open('mappings/mappings.tiny') as f:
                mappings = sanitize_utf8(f.read())

    # save to local cache
    path = CACHE_ROOT + FABRIC_YARN_V2_CACHE % (mc_version, yarn_version)
    with open(path, 'w', encoding='utf-8') as f:
        f.write(mappings)

    return mappings


def load_yarn_intermediary(mc_version: str) -> str:
    path = CACHE_ROOT + FABRIC_INTERMEDIARY_CACHE % mc_version
    if os.path.isfile(path):
        with open(path) as f:
            return f.read()

    url = FABRIC_INTERMEDIARY_URL % mc_version
    with urllib.request.urlopen(url) as request:
        intermediary = sanitize_utf8(request.read())

    # save to local cache
    with open(path, 'w') as f:
        f.write(intermediary)
    return intermediary


def load_mcp_mappings(mc_version: str, mcp_date: Optional[str] = None, mcp_version: Optional[str] = None, urls: Optional[List[str]] = None, cache_location: Optional[str] = None) -> Tuple[str, str, str]:
    # Determine default arguments and versions
    if mcp_date is None:
        mcp_date = str(datetime.date.today()).replace('-', '')
    if mcp_version is None:
        mcp_version = mcp_date
    if cache_location is None:
        cache_location = CACHE_ROOT + MCP_CACHE % (mcp_version, mc_version)
    if urls is None:
        urls = [u % (mcp_version, mc_version, mcp_version, mc_version) for u in DEFAULT_MCP_MAVENS]

    # Check cache first
    if os.path.isdir(cache_location):
        with open(cache_location + '/methods.csv') as f:
            methods = f.read()
        with open(cache_location + '/fields.csv') as f:
            fields = f.read()
        with open(cache_location + '/params.csv') as f:
            params = f.read()
        return methods, fields, params

    # Sequentially try all provided urls for the given version
    export = error = None
    while export is None and urls:
        url = urls.pop(0)
        export, error = try_download(url)
    if export is None:
        raise error

    # Read the successful export
    with io.BytesIO(export) as fio:
        with zipfile.ZipFile(fio, 'r') as export_zip:
            with export_zip.open('methods.csv') as f:
                methods = sanitize_utf8(f.read())
            with export_zip.open('fields.csv') as f:
                fields = sanitize_utf8(f.read())
            try:
                with export_zip.open('params.csv') as f:
                    params = sanitize_utf8(f.read())
            except KeyError:
                params = ''  # Some mappings (yarn2mcp) do not include params

    # Save all files to the local cache
    if not os.path.isdir(cache_location):
        os.mkdir(cache_location)
    with open(cache_location + '/methods.csv', 'w') as f:
        f.write(methods)
    with open(cache_location + '/fields.csv', 'w') as f:
        f.write(fields)
    with open(cache_location + '/params.csv', 'w') as f:
        f.write(params)
    return methods, fields, params


def load_mcpconfig(mc_version: str) -> Tuple[str, str, str]:
    path = CACHE_ROOT + MCP_CONFIG_CACHE % mc_version

    # Check local cache first
    if os.path.isdir(path):
        with open(path + '/joined.tsrg') as f:
            joined = f.read()
        with open(path + '/static_methods.txt') as f:
            static_methods = f.read()
        with open(path + '/constructors.txt') as f:
            constructors = f.read()
        return joined, static_methods, constructors

    # Cache miss, download from forge maven
    url = FORGE_MCP_CONFIG_URL % (mc_version, mc_version)
    with urllib.request.urlopen(url) as request:
        mcp_config = request.read()
    with io.BytesIO(mcp_config) as fio:
        with zipfile.ZipFile(fio, 'r') as mcp_config_zip:
            with mcp_config_zip.open('config/joined.tsrg') as f:
                joined = sanitize_utf8(f.read())
            with mcp_config_zip.open('config/static_methods.txt') as f:
                static_methods = sanitize_utf8(f.read())
            with mcp_config_zip.open('config/constructors.txt') as f:
                constructors = sanitize_utf8(f.read())

    # Save to local cache
    if not os.path.isdir(path):
        os.mkdir(path)
    with open(path + '/joined.tsrg', 'w') as f:
        f.write(joined)
    with open(path + '/static_methods.txt', 'w') as f:
        f.write(static_methods)
    with open(path + '/constructors.txt', 'w') as f:
        f.write(constructors)
    return joined, static_methods, constructors


def load_official(mc_version: str) -> Tuple[str, str]:
    # Need to inspect the manifest
    def load_manifest(use_cache: bool = True) -> Tuple[Dict, bool]:
        official_manifest = CACHE_ROOT + OFFICIAL_MANIFEST_CACHE
        if os.path.isfile(official_manifest) and use_cache:
            # Load manifest from cache
            with open(official_manifest) as f_:
                manifest = f_.read()
            return json.loads(manifest), True
        else:
            # Download and save manifest
            with urllib.request.urlopen(OFFICIAL_MANIFEST_URL) as request_:
                manifest = sanitize_utf8(request_.read())
            with open(official_manifest, 'w') as f_:
                f_.write(manifest)
            return json.loads(manifest), False

    def find_game_version_manifest_matching(manifest_json_in: Dict, mc_version_in: str) -> Optional[str]:
        for game_version_json in manifest_json_in['versions']:
            if game_version_json['id'] == mc_version_in:
                return game_version_json['url']
        return None

    manifest_json = None
    was_cached = False
    if mc_version is None:
        manifest_json, was_cached = load_manifest()
        mc_version = manifest_json['latest']['release']

    # Check the official mapping cache
    mapping_path = CACHE_ROOT + OFFICIAL_MAPPING_CACHE % mc_version
    if os.path.isdir(mapping_path):
        # Official cache found
        with open(mapping_path + '/client.txt') as f:
            client = sanitize_utf8(f.read())
        with open(mapping_path + '/server.txt') as f:
            server = sanitize_utf8(f.read())
        return client, server

    # Need to download the official mappings. Check if the version manifest is present
    version_meta_path = CACHE_ROOT + OFFICIAL_VERSION_MANIFEST_CACHE % mc_version
    if os.path.isfile(version_meta_path):
        # Load the version manifest, in order to get the mapping urls
        with open(version_meta_path) as f:
            version_meta_json = json.loads(f.read())
    else:
        # No version manifest, so load the full manifest
        if manifest_json is None:
            manifest_json, was_cached = load_manifest()

        # Find the version manifest matching the mc version
        version_manifest_url = find_game_version_manifest_matching(manifest_json, mc_version)

        # If not found, and the manifest was cached, then reload the manifest without caching and try again
        if version_manifest_url is None and was_cached:
            manifest_json, was_cached = load_manifest(False)
            version_manifest_url = find_game_version_manifest_matching(manifest_json, mc_version)

        # If still not none, throw an error
        if version_manifest_url is None:
            raise ValueError('No manifest entry for game version %s' % mc_version)

        # Download and save manifest
        with urllib.request.urlopen(version_manifest_url) as request:
            version_manifest = sanitize_utf8(request.read())
        with open(version_meta_path, 'w') as f:
            f.write(version_manifest)

        version_meta_json = json.loads(version_manifest)

    # Identify urls for mappings
    client_url = version_meta_json['downloads']['client_mappings']['url']
    server_url = version_meta_json['downloads']['server_mappings']['url']

    # Load official mappings
    with urllib.request.urlopen(client_url) as request:
        client = sanitize_utf8(request.read())
    with urllib.request.urlopen(server_url) as request:
        server = sanitize_utf8(request.read())

    # And save to cache
    if not os.path.isdir(mapping_path):
        os.mkdir(mapping_path)
    with open(mapping_path + '/client.txt', 'w') as f:
        f.write(client)
    with open(mapping_path + '/server.txt', 'w') as f:
        f.write(server)
    return client, server


def load_mcp_spreadsheet(mc_version: str) -> str:
    path = CACHE_ROOT + FORGE_SPREADSHEET_CACHE % mc_version
    if os.path.isfile(path):
        with open(path) as f:
            return f.read()

    url = FORGE_MCP_SPREADSHEET_URL
    with urllib.request.urlopen(url) as request:
        text = sanitize_utf8(request.read())
    with open(path, 'w') as f:
        f.write(text)

    return text


def load_manual_corrections(mc_version: str) -> Dict[str, str]:
    path = CACHE_ROOT + CORRECTIONS_CACHE % mc_version
    if os.path.isfile(path):
        with open(path) as f:
            text = sanitize_utf8(f.read())
            return json.loads(text)

    with open(path, 'w') as f:
        f.write('{\n}')

    return {}


def try_download(url: str) -> Optional[Any]:
    try:
        with urllib.request.urlopen(url) as request:
            result = request.read()
            return result, None
    except urllib.error.HTTPError as e:
        return None, e


def sanitize_utf8(text) -> str:
    if not isinstance(text, str):
        text = text.decode('utf-8')
    return text.replace('\r\n', '\n').replace('\u200c', '')
