# Use Maven image with JDK 21 (Temurin distribution)
FROM maven:3.9.11-eclipse-temurin-21

# Install AWS CLI v2
RUN apt-get update && \
    apt-get install -y unzip curl && \
    curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" && \
    unzip awscliv2.zip && \
    ./aws/install

CMD ["bash"]