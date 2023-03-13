@echo off
set SMACMEM=1024
IF NOT "%SMAC_MEMORY%"=="" (set SMACMEM=%SMAC_MEMORY%)

set DIR=C:\weka-src
set EXEC=ca.ubc.cs.beta.smac.executors.ValidatorExecutor
java -Xmx%SMACMEM%m -cp "%DIR%\*;%DIR%\lib\*;%DIR%\bin\ " ca.ubc.cs.beta.aeatk.ant.execscript.Launcher %EXEC% %*
