package com.skyer.service;

import com.skyer.cache.CacheService;
import com.skyer.contants.RedisCacheKey;
import com.skyer.mapper.LogMapper;
import com.skyer.vo.Log;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日志服务层
 *
 * @author SKYER
 */
@Service
public class LogService {

    @Resource
    private LogMapper logMapper;
    @Resource
    private CacheService cacheService;

    /**
     * 通过id获取
     *
     * @param id 日志ID
     */
    public Log getById(Integer id) {
        Log log = cacheService.get(RedisCacheKey.GET_LOG_BY_ID + id); // 先读取缓存
        if (log == null) { // double check
            synchronized (this) {
                log = cacheService.get(RedisCacheKey.GET_LOG_BY_ID + id); // 再次从缓存中读取，防止高并发情况下缓存穿透问题
                if (log == null) { // 缓存中没有，再从数据库中读取，并写入缓存
                    log = logMapper.getById(id);
                    cacheService.set(RedisCacheKey.GET_LOG_BY_ID + id, log);
                }
            }
        }
        return log;
    }

    /**
     * 分页获取日志
     *
     * @param pageNum  页码
     * @param pageSize 每页显示数量
     */
    public List<Log> getByPage(Integer pageNum, Integer pageSize) {
        List<Log> list = cacheService.get(RedisCacheKey.GET_LOG_BY_PAGE + pageNum); // 先读取缓存
        if (list == null) { // double check
            synchronized (this) {
                list = cacheService.get(RedisCacheKey.GET_LOG_BY_PAGE + pageNum); // 再次从缓存中读取，防止高并发情况下缓存穿透问题
                if (list == null) { // 缓存中没有，再从数据库中读取，并写入缓存
                    list = logMapper.getByPage((pageNum - 1) * pageSize, pageSize);
                    cacheService.set(RedisCacheKey.GET_LOG_BY_PAGE + pageNum, list);
                }
            }
        }
        return list;
    }

    /**
     * 获取日志总数量
     */
    public Long getTotalNum() {
        Long totalNum = cacheService.get(RedisCacheKey.GET_LOG_TOTAL_NUM); // 先读取缓存
        if (totalNum == null) { // double check
            synchronized (this) {
                totalNum = cacheService.get(RedisCacheKey.GET_LOG_TOTAL_NUM); // 再次从缓存中读取，防止高并发情况下缓存穿透问题
                if (totalNum == null) { // 缓存中没有，再从数据库中读取，并写入缓存
                    totalNum = logMapper.getTotalNum();
                    cacheService.set(RedisCacheKey.GET_LOG_TOTAL_NUM, totalNum);
                }
            }
        }
        return totalNum;
    }

    /**
     * 添加
     */
    public void add(Log log) {
        logMapper.add(log);
    }

    /**
     * 添加日志
     */
    public void addLog(String title, String content, Integer operatorId, String operatorIp) {
        Log log = new Log();
        log.setTitle(title);
        log.setContent(content);
        log.setOperatorId(operatorId);
        log.setOperatorIp(operatorIp);
        log.setOperatorTime(new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        this.add(log);
    }

}