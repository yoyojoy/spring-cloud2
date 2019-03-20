package com.shengyecapital.business.mybatis;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MybatisGenerator {

    @Test
    public void generator() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(MybatisGenerator.class.getClassLoader().getResourceAsStream("generatorConfig.xml"));
        String cpath = MybatisGenerator.class.getClassLoader().getResource("generatorConfig.xml").toString();
        cpath = cpath.substring(0, cpath.indexOf("target")).replace("file:/", "");
        Context context = config.getContexts().get(0);
        context.getJavaModelGeneratorConfiguration().setTargetProject(cpath + context.getJavaModelGeneratorConfiguration().getTargetProject());
        context.getSqlMapGeneratorConfiguration().setTargetProject(cpath + context.getSqlMapGeneratorConfiguration().getTargetProject());
        context.getJavaClientGeneratorConfiguration().setTargetProject(cpath + context.getJavaClientGeneratorConfiguration().getTargetProject());
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }


}
