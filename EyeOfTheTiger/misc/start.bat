@echo off

set CURRENT=%CD%
set JAVA_HOME=%CURRENT%\Application\jre
set PATH=%JAVA_HOME%\bin;%PATH%

REM start /B "%JAVA_HOME%\bin\java.exe" -Duser.dir="%CURRENT%"\Projets\ -jar "%CURRENT%\Application\EyeOfTheTiger.jar"
REM -Duser.dir="%CURRENT%"\Projets\


START "" /B "%JAVA_HOME%\bin\javaw.exe" -Duser.dir="%CURRENT%"\Projets\ -jar "%CURRENT%\Application\EyeOfTheTiger.jar"


