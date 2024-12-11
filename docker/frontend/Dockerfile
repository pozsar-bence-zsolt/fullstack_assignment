FROM node:20.17-alpine3.19

WORKDIR /app

COPY . .

WORKDIR /app/darts-fighters

RUN npm install
RUN npm install -g @angular/cli

EXPOSE 49153
EXPOSE 4200
