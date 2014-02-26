package com.taotaosou.data.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @ClassName		BaseDao
 * @Description		ibatis操作数据库的基类
 * @author			joe.zhao
 * @date			2010-6-27 上午11:02:46
 */
public abstract class BaseDao {

    private static final Logger logger = Logger.getLogger(BaseDao.class);

    @Resource(name = "spiderClient")
    protected SqlMapClient      client;

//    @Resource(name = "spiderSqlMapClient")
//    protected SqlMapClient      spiderClient;
//
//    @Resource(name = "findSqlMapClient")
//    protected SqlMapClient      findSqlMapClient;
//
//    @Resource(name = "matchSqlMapClient")
//    protected SqlMapClient      matchSqlMapClient;
//
//    protected SqlMapClient getSpiderClient() {
//        return spiderClient;
//    }
//
//    protected void setSpiderClient(SqlMapClient spiderClient) {
//        this.spiderClient = spiderClient;
//    }

    // 如果是多数据源时，可以更换数据源使用
    protected void setClient(SqlMapClient client) {
        this.client = client;
    }

    protected int delete(String id) {
        try {
            return client.delete(id);
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected int delete(String id, Object o) {
        try {
            return client.delete(id, o);
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected int update(String id) {
        try {
            return client.update(id);
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected int update(String id, Object o) {
        try {
            return client.update(id, o);
        } catch (Exception e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected Object insert(String id) {
        try {
            return client.insert(id);
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected Object insert(String id, Object o) {
        try {
            Object obj = client.insert(id, o);
            return obj;
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected List<?> list(String id) {
        try {
            return client.queryForList(id);
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected List<?> list(String id, Object o) {
        try {
            return client.queryForList(id, o);
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected Map<?, ?> map(String id, Object parameterObject, String keyProp) {
        try {
            return client.queryForMap(id, parameterObject, keyProp);
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected Map<?, ?> map(String id, Object parameterObject, String keyProp, String valueProp) {
        try {
            return client.queryForMap(id, parameterObject, keyProp, valueProp);
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected Object object(String id) {
        try {
            return client.queryForObject(id);
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected Object object(String id, Object o) {
        try {
            return client.queryForObject(id, o);
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected void startBatch() {
        try {
            client.startBatch();
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected int executeBatch() {
        try {
            return client.executeBatch();
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected void startTransaction() {
        try {
            client.startTransaction();
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    protected void commitTransaction() {
        try {
            client.commitTransaction();
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    protected void endTransaction() {
        try {
            client.endTransaction();
        } catch (SQLException e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
        }
    }

    /**
     * 分页查询
     * 
     * @param sqlID
     *            Ibatis中的SQLID 如果是select count(1)的话，必须以——count结尾
     * @param map
     *            存放相关的查询条件
     * @return
     */
    protected List<?> page(String sqlID, Map<String, Object> map) {
        try {
            Pager pager = (Pager) map.get("pager");
            if (pager != null) {
                Integer totalCount = (Integer) client.queryForObject(sqlID + "_count", map);
                pager.setItemCount(totalCount);
            }

            List<?> queryForList = client.queryForList(sqlID, map);
            return queryForList;
        } catch (Exception e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    protected List<?> page(String sqlID, Pager pager, Object obj) {
        try {
            Map<String, Object> hm = new HashMap<String, Object>();
            hm.put("obj", obj);
            hm.put("pager", pager);
			if (pager != null && pager.getItemCount() == null) {
				Integer totalCount = (Integer) client.queryForObject(sqlID + "_count", obj);
				pager.setItemCount(totalCount);
			}

            List<?> queryForList = client.queryForList(sqlID, hm);
            return queryForList;
        } catch (Exception e) {
            // e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

//    protected int delete_spider(String id) {
//        try {
//            return spiderClient.delete(id);
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected int delete_spider(String id, Object o) {
//        try {
//            return spiderClient.delete(id, o);
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected int update_spider(String id) {
//        try {
//            return spiderClient.update(id);
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected int update_spider(String id, Object o) {
//        try {
//            return spiderClient.update(id, o);
//        } catch (Exception e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected Object insert_spider(String id) {
//        try {
//            return spiderClient.insert(id);
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected Object insert_spider(String id, Object o) {
//        try {
//            Object obj = spiderClient.insert(id, o);
//            return obj;
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected List<?> list_spider(String id) {
//        try {
//            return spiderClient.queryForList(id);
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected List<?> list_spider(String id, Object o) {
//        try {
//            return spiderClient.queryForList(id, o);
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected Object object_spider(String id) {
//        try {
//            return spiderClient.queryForObject(id);
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected Object object_spider(String id, Object o) {
//        try {
//            return spiderClient.queryForObject(id, o);
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected Object insert_find(String id, Object o) {
//        try {
//            Object obj = findSqlMapClient.insert(id, o);
//            return obj;
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected List<?> list_find(String id, Object o) {
//        try {
//            return findSqlMapClient.queryForList(id, o);
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected List<?> list_find(String id) {
//        try {
//            return findSqlMapClient.queryForList(id);
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected Object update_find(String id, Object o) {
//        try {
//            Object obj = findSqlMapClient.update(id, o);
//            return obj;
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected Object object_find(String id, Object o) {
//        try {
//            Object obj = findSqlMapClient.queryForObject(id, o);
//            return obj;
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected Object delete_find(String id, Object o) {
//        try {
//            Object obj = findSqlMapClient.delete(id, o);
//            return obj;
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    /**
//     * 
//     */
//
//    protected Object insert_match(String id, Object o) {
//        try {
//            Object obj = matchSqlMapClient.insert(id, o);
//            return obj;
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected List<?> list_match(String id, Object o) {
//        try {
//            return matchSqlMapClient.queryForList(id, o);
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected Object update_match(String id, Object o) {
//        try {
//            Object obj = matchSqlMapClient.update(id, o);
//            return obj;
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected Object object_match(String id, Object o) {
//        try {
//            Object obj = matchSqlMapClient.queryForObject(id, o);
//            return obj;
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected Object delete_match(String id, Object o) {
//        try {
//            Object obj = matchSqlMapClient.delete(id, o);
//            return obj;
//        } catch (SQLException e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }
//
//    protected List<?> page_match(String sqlID, Pager pager, Object obj) {
//        try {
//            Map<String, Object> hm = new HashMap<String, Object>();
//            hm.put("obj", obj);
//            hm.put("pager", pager);
//            if (pager != null) {
//                Integer totalCount = (Integer) matchSqlMapClient.queryForObject(sqlID + "_count",
//                    obj);
//                pager.setItemCount(totalCount);
//            }
//
//            List<?> queryForList = matchSqlMapClient.queryForList(sqlID, hm);
//            return queryForList;
//        } catch (Exception e) {
//            // e.printStackTrace();
//            logger.error(e.getMessage());
//            throw new RuntimeException();
//        }
//    }

}
