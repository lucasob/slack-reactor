# Finger Guns

`Finger Guns` is a one-of-a-kind Slack app. It does nothing other than apply automatic finger guns to *any* message in a
channel that it is added to

## Deployment

### Docker

The dockerfile will build the entire up, and is designed to set up such that it can build from source anywhere it is
called from.

The build requires two arguments locally:
* token - the Slack API Token
* api - the root URL for the Slack API

```
docker build -t {DESIRED_TAG} -f Dockerfile --env SLACK_AUTH_TOKEN={TOKEN} --env SLACK_API_URL=https://slack.com/api .
```

## Environment

`SLACK_API_URL` = https://slack.com/api
`SLACK_AUTH_TOKEN` = Bot token required for auth to slack