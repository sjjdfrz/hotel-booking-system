# Build stage
FROM docker.arvancloud.ir/node:18 AS builder
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

# Production stage with Nginx
FROM docker.arvancloud.ir/nginx:stable-alpine
COPY --from=builder /app/dist /usr/share/nginx/html

# Custom Nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
