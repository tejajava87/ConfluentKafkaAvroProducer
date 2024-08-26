public interface LogRepository extends JpaRepository<Log, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO LOG_T " +
            "(KTI_TOPIC, KTI_APP_NAME, KTI_TYPE, KTI_JSON, KTI_STATUS, KTI_ERROR_MESSAGE, KTI_ERROR_CNT, KTI_CREATED_TIMESTAMP, KTI_LAST_UPDATED_TIMESTAMP) " +
            "VALUES (:topicName, :appName, :type, :jsonString, :status, :errorMessage, :errorCnt, :createdTimestamp, :updatedTimestamp)", 
            nativeQuery = true)
    void insertRecord(@Param("topicName") String topicName,
                      @Param("appName") String appName,
                      @Param("type") String type,
                      @Param("jsonString") String jsonString,
                      @Param("status") String status,
                      @Param("errorMessage") String errorMessage,
                      @Param("errorCnt") int errorCnt,
                      @Param("createdTimestamp") LocalDateTime createdTimestamp,
                      @Param("updatedTimestamp") LocalDateTime updatedTimestamp);

    @Modifying
    @Transactional
    @Query(value = "UPDATE LOG_T " +
            "SET KTI_TOPIC = :topicName, " +
            "KTI_APP_NAME = :appName, " +
            "KTI_TYPE = :type, " +
            "KTI_JSON = :jsonString, " +
            "KTI_STATUS = :status, " +
            "KTI_ERROR_MESSAGE = :errorMessage, " +
            "KTI_ERROR_CNT = :errorCnt, " +
            "KTI_LAST_UPDATED_TIMESTAMP = :updatedTimestamp " +
            "WHERE KTI_ID = :id", 
            nativeQuery = true)
    void updateRecord(@Param("id") Long id,
                      @Param("topicName") String topicName,
                      @Param("appName") String appName,
                      @Param("type") String type,
                      @Param("jsonString") String jsonString,
                      @Param("status") String status,
                      @Param("errorMessage") String errorMessage,
                      @Param("errorCnt") int errorCnt,
                      @Param("updatedTimestamp") LocalDateTime updatedTimestamp);
}
