FROM node:16.17.0 as build-stage

WORKDIR /var/jenkins_home/workspace/football-well-known-frontend/Front/soccer
RUN npm cache verify
RUN npm cache clean --force
COPY package*.json ./ 
RUN npm install --cache
COPY . .
RUN npm run build
FROM nginx:stable-alpine as production-stage
\
COPY --from=build-stage /var/jenkins_home/workspace/football-well-known-frontend/Front/soccer/build /usr/share/nginx/html
COPY --from=build-stage /var/jenkins_home/workspace/football-well-known-frontend/Front/soccer/default.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g","daemon off;"]
