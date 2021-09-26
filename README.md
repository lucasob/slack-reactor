# Finger Guns

`Finger Guns` is a one-of-a-kind Slack app. It does nothing other than apply automatic finger guns to *any* message in a
channel that it is added to

## Docker

The dockerfile will build the entire app, and is designed to set up such that it can build from source anywhere it is
called from.

### Local

```
# To build
docker build -t {DESIRED_TAG} -f Dockerfile .

# To Run
docker run -t {DESIRED_TAG} --env SLACK_AUTH_TOKEN={TOKEN} --env SLACK_API_URL=https://slack.com/api --port 8080:8080
```

### Deployment

Currently, this is deployed to heroku, but given it's in a delicious little container you can deploy it where ever your
little heart desires (please not Azure).

## Environment

In Heroku, these need to be manually set on your dyno. The above docker run instruction offers an example for getting
them in the container yourself.

* `SLACK_API_URL` = https://slack.com/api

* `SLACK_AUTH_TOKEN` = Bot token required for auth to slack

* `SLACK_REACTION_TYPE` = The name of the reaction to apply. Do not include surrounding colons

## Slack

Taking a second here to say `@slack: pls fix; docco sux`

The best usage here is configuring this app to run using a *User Token*, where the app will react as the user who
installed the app to the workspace in slack.

*Bot Token Scopes*
* `channels:join`

*User Token Scopes*
* `channels:history`
* `reactions:write`

*Events are enabled*

*Subscribe to events on behalf of users:*
* `message.channels` -> Honestly, fucks me if this is required.

