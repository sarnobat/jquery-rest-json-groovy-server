cd client
# Mac
open "index.html"
# Linux
# xdg-open "index.html"

cd ..

cd server
cd .groovy/lib
mkdir -p ~/.groovy/lib
mv *jar ~/.groovy/lib/
cd ../..

groovy webserver_json.groovy