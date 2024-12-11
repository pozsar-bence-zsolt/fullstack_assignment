FROM node:20.17-alpine3.19

WORKDIR /app

COPY . .

RUN npm install -g @angular/cli

WORKDIR /app/darts-fighters

RUN npm install
RUN ng update @angular/cli @angular/core

EXPOSE 49153
EXPOSE 4200
