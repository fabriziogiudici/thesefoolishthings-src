#!/bin/sh
#***********************************************************************************************************************
#*
#* These Foolish Things - Miscellaneous utilities
#* Copyright (C) 2009 - 2021 by Tidalwave s.a.s. (http://www.tidalwave.it)
#*
#***********************************************************************************************************************
#*
#* Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
#* the License. You may obtain a copy of the License at
#*
#*     http://www.apache.org/licenses/LICENSE-2.0
#*
#* Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
#* an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the License for the
#* specific language governing permissions and limitations under the License.
#*
#***********************************************************************************************************************
#*
#* WWW: http://thesefoolishthings.tidalwave.it
#* SCM: https://bitbucket.org/tidalwave/thesefoolishthings-src
#*
#***********************************************************************************************************************
#

set -e -x

rm -rfv archetype-test

mvn archetype:generate -B \
    -DarchetypeCatalog=local \
    -DarchetypeVersion=1.0.15-SNAPSHOT \
    -DarchetypeGroupId=it.tidalwave.thesefoolishthings \
    -DarchetypeArtifactId=project-archetype \
    -DgroupId=com.acme \
    -DartifactId=archetype-test \
    -DorganizationName=Acme \
    -DorganizationUrl=http://acme.com \
    -DprojectName=ArchiTest

