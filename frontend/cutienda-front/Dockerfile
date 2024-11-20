# build stage
FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# production stage
FROM nginx:stable-alpine as production-stage

# Copiar la configuración de Nginx
COPY nginx.conf /etc/nginx/nginx.conf
# O la ubicación que desees

# Copiar los archivos construidos de Vue a la carpeta de Nginx
COPY --from=build-stage /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]