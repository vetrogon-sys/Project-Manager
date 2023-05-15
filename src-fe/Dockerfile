FROM --platform=$BUILDPLATFORM node:lts AS development

WORKDIR /app/fe
COPY package.json /app/fe/package.json
COPY package-lock.json /app/fe/package-lock.json

RUN npm install
COPY ./ /app/fe/

ENV CI=false

CMD [ "npm", "start" ]

FROM development AS build

RUN ["npm", "run", "build"]