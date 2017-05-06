# GYL-Server API

<!-- MarkdownTOC -->

- [三角定位](#%E4%B8%89%E8%A7%92%E5%AE%9A%E4%BD%8D)

<!-- /MarkdownTOC -->

<a name="%E4%B8%89%E8%A7%92%E5%AE%9A%E4%BD%8D"></a>
### 三角定位

Request URI:

```
GET /api/localization
```

Request Parameters:

| Param | Description |
|-------|-------------|
|alpha|alpha 值|
|beta|beta 值|
|x1|第一个参照物的横坐标|
|y1|第一个参照物的纵坐标|
|x2|第二个参照物的横坐标|
|y2|第二个参照物的纵坐标|
|x3|第三个参照物的横坐标|
|y3|第三个参照物的纵坐标|

Response Properties:

| Property | Description | Type |
|----------|-------------|------|
|x|用户位置横坐标|float|
|y|用户位置纵坐标|float|

Response Example:

```json
{
    "x": 136.35,
    "y": 230.55
}
```
