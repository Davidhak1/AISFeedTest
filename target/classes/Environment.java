package resources;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:props/${env}.properties"
//        "src/main/java/resources/:${env}.properties"
})
public interface Environment extends Config {

    String aisUrl();
    String aisUser();
    String aisPass();
    String aisDBName();

    String mysqlUrl();
    String mysqlUser();
    String mysqlPass();
    String dbName();

    String nexusURL();
    String nexusUser();
    String nexusPass();
    String nexusDbName();

    String ftpUrl();
    String ftpUSer();
    String ftpPassword();

    @Key("ais-base-path")
    String ais_base_path();

    String aisSaveDir();
    String aisProcessSaveDir();

}