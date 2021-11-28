package web.spring.boot.component;


import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis Mapper 动态生成
 * <p>1、先注册 sqlStatement </p>
 * <p>2、通过调用 sqlStatement id 来使用 mapper 相关的功能</p>
 */
public class MybatisMapper {

    private static final Logger logger = LoggerFactory.getLogger(MybatisMapper.class);

    SqlSessionFactory factory;

    String namespace;

    final Configuration configuration;

    LanguageDriver languageDriver;


    /**
     * 注册 Mapper
     * 注册指定命名空间的 Mapper，可以对该命名空间添加相应的SQL声明
     * @param namespace Mapper 类
     * @param factory sqlSession
     */
    public MybatisMapper(String namespace, SqlSessionFactory factory)
    {
        this.namespace = namespace;
        this.factory = factory;
        this.configuration = factory.getConfiguration();
        this.languageDriver = configuration.getDefaultScriptingLanguageInstance();

        //configuration.getMappedStatements();
    }

    private String ensureId(String id) {
        if (null == id)
            return null;
        return id.startsWith(namespace) ? id : namespace + "." + id;
    }


    private void addParameterMap(String id, Class<?> parameterType) {

        String parameterMapId = ensureId(id);

        removeParameterMap(parameterMapId);

        synchronized (configuration) {
            ParameterMap parameterMap = new ParameterMap.Builder(
                    configuration,
                    parameterMapId,
                    parameterType,
                    new ArrayList<ParameterMapping>(0)
            ).build();

            configuration.addParameterMap(parameterMap);
        }
    }

    private boolean removeParameterMap(String id) {
        try {
            synchronized (configuration) {
                String parameterMapId = ensureId(id);
                ParameterMap parameterMap = configuration.getParameterMap(parameterMapId);
                if (null != parameterMap)
                    configuration.getParameterMapNames().remove(parameterMapId);
                return true;
            }
        } catch (IllegalArgumentException e) {
            logger.info("没有此 parameterMap[" + id + "], " +
                    "可以注入此 parameterMap 到 configuration 中");
        }
        return false;
    }


    private SqlSource createSqlSource(String sql, Class<?> parameterType)
    {
        String script = "<script>" + sql + "</script>";
        // RowBounds rowBounds;
        new SqlSourceBuilder(configuration);
        return languageDriver.createSqlSource(configuration, script, parameterType);
    }



    /**
     * 添加 SQL 声明
     * @param id sql 标识
     * @param sql 执行的 SQL
     * @param commandType SQL 类型
     * @param resultMapId ResultMap 标识
     */
    public void addSqlStatement(String id,
                                String sql,
                                SqlCommandType commandType,
                                Class<?> parameterType,
                                String resultMapId)
    {

        String mapperId = ensureId(id);

        removeSqlStatement(mapperId);

        synchronized (configuration){

            List<ResultMap> resultMaps = new ArrayList<>();

            resultMaps.add(getResultMap(resultMapId));

            // 构建一个 select 类型的ms ，通过制定SqlCommandType.SELECT
            // -》 注意，这里是SELECT,其他的UPDATE\INSERT 还需要指定成别的
            // 注意！！-》 这里可以指定你想要的任何配置，比如cache,CALLABLE,
            MappedStatement statement = new MappedStatement.Builder (
                    configuration,
                    mapperId,
                    createSqlSource(sql, parameterType),
                    commandType)
                    .resultMaps(resultMaps)
                    .build();
            logger.info("addMappedStatement: {}, sql: {}", mapperId, sql);
            // 加入到 configuration 中去
            configuration.addMappedStatement(statement);
        }

    }

    /**
     * 添加 SQL 声明
     * @param id sql 标识
     * @param sql 执行的 SQL
     * @param commandType SQL 类型
     * @param resultType 返回值类型
     */
    public void addSqlStatement(String id,
                                String sql,
                                SqlCommandType commandType,
                                Class<?> parameterType,
                                Class<?> resultType)
    {

        if (!hasResultMap(resultType.getSimpleName()))
            addResultMap(resultType.getSimpleName(), resultType);

        addSqlStatement(id, sql, commandType, parameterType, resultType.getSimpleName());


    }

    public boolean hasSqlStatement(String id) {
        try{
            String mapperId = ensureId(id);
            MappedStatement statement = configuration.getMappedStatement(mapperId);
            logger.info("hasSqlStatement. id: {}, result: {}"
                    , mapperId
                    , (null != statement));
            return (null != statement);
        } catch (IllegalArgumentException e){
            return false;
        }
    }

    public void removeSqlStatement(String id) {
        String mapperId = ensureId(id);
        try{
            synchronized (configuration) {   // 防止并发插入多次
                if (hasSqlStatement(mapperId)) {
                    configuration.getMappedStatementNames().remove(mapperId);
                }
            }
        }catch (IllegalArgumentException e){
            logger.info("没有此 mappedStatement[" + id + "], " +
                    "可以注入此 mappedStatement 到 configuration 中");
        }
    }


    public void addResultMap(String id, Class<?> resultType) {
        // ArrayList<ResultMap> resultMaps = new ArrayList<ResultMap>();
        // resultMaps.add(resultMap);
        String resultMapId = ensureId(id);

        removeResultMap(resultMapId);

        synchronized (configuration) {   // 防止并发插入多次
            ResultMap resultMap = new ResultMap.Builder (
                    configuration,
                    resultMapId,
                    resultType,
                    new ArrayList<>(0)
            ).build();
            configuration.addResultMap(resultMap);
        }
    }

    public ResultMap getResultMap(String id) {
        String resultMapId = ensureId(id);
        return  configuration.getResultMap(resultMapId);
    }

    public boolean hasResultMap(String id) {
        String resultMapId = ensureId(id);
        try{
            ResultMap resultMap = configuration.getResultMap(resultMapId);
            return (null != resultMap);
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    public void removeResultMap(String id) {
        String resultMapId = ensureId(id);
        try{
            synchronized (configuration) {   // 防止并发插入多次
                if (hasResultMap(resultMapId)) {
                    configuration.getResultMapNames().remove(resultMapId);
                }
            }
        }catch (IllegalArgumentException e){
            logger.info("没有此 resultMap[" + id + "], " +
                    "可以注入此 resultMap 到 configuration  中");
        }
    }


    // ======
    public <E> List<E> selectList(String id, Object parameter)
    {
        String mapperId = ensureId(id);

        if (!hasSqlStatement(mapperId)) {
            logger.error("selectList has an exception. id: {}, exception: {}"
                    , mapperId
                    , "not exists mapped statement in " + configuration.getMappedStatementNames());
            return null;
        }
        MappedStatement mapper = configuration.getMappedStatement(mapperId);

        String mapperSql = mapper.getSqlSource().toString();

        logger.error("id: {}, sql: {}", mapperId, mapperSql);

        try (SqlSession session = factory.openSession()) {
            return session.selectList(mapperId, parameter);
        } catch (Exception e) {
            logger.error("id: {}, sql: {}, exception: {}", mapperId, mapperSql, e);
            return null;
        }
    }

}
