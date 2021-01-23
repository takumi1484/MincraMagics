# MincraMagics

MincraServer2の独自プラグイン。[MincraMagicRod](https://github.com/celtas/MincraMagicRod)の後継。


# Dependencies

NBT-API: [SpigotMC](https://www.spigotmc.org/resources/nbt-api.7939/) [GitHub](https://github.com/tr7zw/Item-NBT-API)


# Commands

|Command|Description|
|:---|:---|
|/mcr item get <MCR_ID>|items.jsonで定義されたアイテムをMCR_IDから取得する。|


# Setting Files

/data/items.json
```json
[
  {
    "id": "apple",
    "mcr_id": "example",
    "nbt": {
      "display":{
        "Name": "{\"text\":\"Example Item\",\"color\":\"white\",\"bold\":true,\"italic\":false}",
        "Lore":[
          "{\"text\":\"This is a example item.\",\"color\":\"gray\",\"italic\":false}",
          "{\"text\":\"Here is 2nd line.\",\"color\":\"gray\",\"italic\":false}"]
        },
      "CustomModelData": 810,
      "HideFlags": 1,
      "Enchantments":[{
        "id": "minecraft:fortune",
        "lvl": 10
      }],
      "MincraMagics":{
      }
    },
    "craftable": true,
    "recipe": {
      "shape": [
        " a ",
        "aba",
        " a "],
      "ingredient": {
        "a": "red_dye",
        "b": "apple"
      }
    }
  }
]
```
