#!/bin/bash

# If you want to use my bash password script. It can be found here @
# https://github.com/jozsefmorrissey/BashScripts/blob/master/confidentalInfo.sh

sudo confidentalInfo.sh replace dbinfo AboutMe ./OracleDBSimpleSetup.sql

password=$(confidentalInfo.sh value dbinfo system)
echo exit | sqlplus system/oracle@localhost:1523/xe @./OracleDBSimpleSetup.sql
#sqlplus system/$password @./OracleDBSimpleSetup.sql

sudo confidentalInfo.sh remove dbinfo AboutMe ./OracleDBSimpleSetup.sql
