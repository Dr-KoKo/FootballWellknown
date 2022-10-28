FROM node:16.17.0 as build-stage
ARG frontend=/var/jenkins_home/workspace/football-well-known-frontend/Front/soccer

WORKDIR ${frontend}
COPY package*.json ./

RUN npm install 
COPY . .
RUN npm run build
FROM nginx:stable-alpine as production-stage

ARG frontend=/var/jenkins_home/workspace/football-well-known-frontend/Front/soccer
COPY --from=build-stage ${frontend}/build /usr/share/nginx/html
COPY --from=build-stage ${frontend}/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g","daemon off;"]
