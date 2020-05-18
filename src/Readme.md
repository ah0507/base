项目业务说明
-

* 关于tb_folder表中字段说明：

    1. type_分为手动和自动，自动为创建user/dept等时一同创建；手动为单独创建
    2. is_leaf表示是否为叶子节点，用户的tb_user_write_folder中的对应的可写入文件夹的is_leaf必须为1，
    而且当tb_folder中的文件夹is_leaf为1时，将不能在创建新文件夹时将其作为上级。
    

* 关于tb_user_write_folder表中字段说明：

    1. folder_id和folder_name的对应以及名称应该和tb_folder中数据一致
    2. is_default表示是否默认，如果为1则为默认值，即选择文件夹框中默认选中