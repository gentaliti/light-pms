FROM node:14.21.3-alpine3.17

WORKDIR /app

COPY package.json ./

COPY ./ ./

RUN npm install

CMD ["npm", "start"]