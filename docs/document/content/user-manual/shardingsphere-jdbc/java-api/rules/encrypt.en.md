+++
title = "Encryption"
weight = 4
+++

## Background

The data encryption Java API rule configuration allows users to directly create ShardingSphereDataSource objects by writing java code. The Java API configuration method is very flexible and can integrate various types of business systems without relying on additional jar packages.

## Parameters

### Root Configuration

Class name: org.apache.shardingsphere.encrypt.api.config.EncryptRuleConfiguration

Attributes:

| *Name*                    | *DataType*                                  | *Description*                                                                                  | *Default Value* |
|---------------------------|---------------------------------------------|------------------------------------------------------------------------------------------------|-----------------|
| tables (+)                | Collection\<EncryptTableRuleConfiguration\> | Encrypt table rule configurations                                                              |                 |
| encryptors (+)            | Map\<String, AlgorithmConfiguration\>       | Encrypt algorithm name and configurations                                                      |                 |

### Encrypt Table Rule Configuration

Class name: org.apache.shardingsphere.encrypt.api.config.rule.EncryptTableRuleConfiguration

Attributes:

| *Name*                    | *DataType*                                   | *Description*                                                       |
|---------------------------|----------------------------------------------|---------------------------------------------------------------------|
| name                      | String                                       | Table name                                                          |
| columns (+)               | Collection\<EncryptColumnRuleConfiguration\> | Encrypt column rule configurations                                  |

### Encrypt Column Rule Configuration

Class name: org.apache.shardingsphere.encrypt.api.config.rule.EncryptColumnRuleConfiguration

Attributes:

| *Name*                     | *DataType* | *Description*                                                        |
|----------------------------|------------|----------------------------------------------------------------------|
| logicColumn                | String     | Logic column name                                                    |
| cipherColumn               | String     | Cipher column name                                                   |
| assistedQueryColumn (?)    | String     | Assisted query column name                                           |
| likeQueryColumn (?)        | String     | Like query column name                                               |
| plainColumn (?)            | String     | Plain column name                                                    |
| encryptorName              | String     | Encrypt algorithm name                                               |
| assistedQueryEncryptorName | String     | Assisted query encrypt algorithm name                                |
| likeQueryEncryptorName     | String     | Like query encrypt algorithm name                                    |

### Encrypt Algorithm Configuration

Class name: org.apache.shardingsphere.infra.config.algorithm.AlgorithmConfiguration

Attributes:

| *Name*     | *DataType* | *Description*                |
|------------|------------|------------------------------|
| name       | String     | Encrypt algorithm name       |
| type       | String     | Encrypt algorithm type       |
| properties | Properties | Encrypt algorithm properties |

Please refer to [Built-in Encrypt Algorithm List](/en/user-manual/common-config/builtin-algorithm/encrypt) for more details about type of algorithm.

## Procedure

1. Create a real data source mapping relationship, where key is the logical name of the data source and value is the datasource object.
2. Create the encryption rule object EncryptRuleConfiguration, and initialize the encryption table object EncryptTableRuleConfiguration, encryption algorithm and other parameters in the object.
3. Call createDataSource of ShardingSphereDataSourceFactory to create  ShardingSphereDataSource.

## Sample

```java
public final class EncryptDatabasesConfiguration {
    
    public DataSource getDataSource() throws SQLException {
        Properties props = new Properties();
        props.setProperty("aes-key-value", "123456");
        EncryptColumnRuleConfiguration columnConfigAes = new EncryptColumnRuleConfiguration("username", "username", "", "", "username_plain", "name_encryptor", null);
        EncryptColumnRuleConfiguration columnConfigTest = new EncryptColumnRuleConfiguration("pwd", "pwd", "assisted_query_pwd", "like_pwd", "", "pwd_encryptor", null);
        EncryptTableRuleConfiguration encryptTableRuleConfig = new EncryptTableRuleConfiguration("t_user", Arrays.asList(columnConfigAes, columnConfigTest), null);
        Map<String, AlgorithmConfiguration> encryptAlgorithmConfigs = new HashMap<>();
        encryptAlgorithmConfigs.put("name_encryptor", new AlgorithmConfiguration("AES", props));
        encryptAlgorithmConfigs.put("pwd_encryptor", new AlgorithmConfiguration("assistedTest", props));
        Map<String, AlgorithmConfiguration> likeEncryptAlgorithmConfigs = new HashMap<>();
        likeEncryptAlgorithmConfigs.put("like_encryptor", new AlgorithmConfiguration("CHAR_DIGEST_LIKE", new Properties()));
        EncryptRuleConfiguration encryptRuleConfig = new EncryptRuleConfiguration(Collections.singleton(encryptTableRuleConfig), encryptAlgorithmConfigs, likeEncryptAlgorithmConfigs);
        return ShardingSphereDataSourceFactory.createDataSource(DataSourceUtil.createDataSource("demo_ds"), Collections.singleton(encryptRuleConfig), props);
    }
}
```

## Related References

- [The feature description of Data Encryption](/en/features/encrypt/ )
- [Dev Guide of Data Encryption](/en/dev-manual/encryption/)
