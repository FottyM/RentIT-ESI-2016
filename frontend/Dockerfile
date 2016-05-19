FROM mhart/alpine-node:5
COPY $PWD /source/app
WORKDIR /source/app
EXPOSE 8000
RUN npm install -g gulp gulp-cli typings typescript ts-node concurrently lite-server
RUN npm install --no-bin-links
RUN npm install --no-bin-links
RUN npm run build
ENTRYPOINT npm run start



