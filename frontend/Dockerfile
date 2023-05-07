FROM node:18.16

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY package.json /app/
RUN npm install --silent
RUN npm install react-scripts@3.4.1 -g --silent

COPY . /app

CMD ["npm", "run", "build"]