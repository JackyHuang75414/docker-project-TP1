FROM httpd:alpine

# 复制配置文件
COPY httpd.conf /usr/local/apache2/conf/httpd.conf

# 复制静态文件（如果需要）
COPY ./htdocs/ /usr/local/apache2/htdocs/