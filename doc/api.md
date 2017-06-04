# API

<!-- MarkdownTOC -->

- [三角定位](#三角定位)

<!-- /MarkdownTOC -->

<a name="三角定位"></a>
### 三角定位

Request URI:

```
GET /api/localization
```

Request Parameters:

| Param | Description | Type |
|-------|-------------|------|
|alpha|alpha 值|float|
|beta|beta 值|float|
|x1|第一个参照物的横坐标|float|
|y1|第一个参照物的纵坐标|float|
|x2|第二个参照物的横坐标|float|
|y2|第二个参照物的纵坐标|float|
|x3|第三个参照物的横坐标|float|
|y3|第三个参照物的纵坐标|float|

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
