# Credit to:
# https://medium.com/gitpod/developing-native-ui-applications-in-gitpod-15af2967c24e

FROM gitpod/workspace-full-vnc:latest

# install dependencies
RUN apt-get update \
    && apt-get install -y libx11-dev libxkbfile-dev libsecret-1-dev libgconf2-dev libnss3 libgtk-3-dev libasound2-dev twm \
    && apt-get clean && rm -rf /var/cache/apt/* && rm -rf /var/lib/apt/lists/* && rm -rf /tmp/*
