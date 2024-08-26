@Repository
public class Repository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RecordLogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void batchInsertRecords(List<RecordLog> recordLogs) {
        String sql = "INSERT INTO LOG_T " +
                     "(KTI_TOPIC, KTI_APP_NAME, KTI_TYPE, KTI_JSON, KTI_STATUS, KTI_ERROR_MESSAGE, " +
                     "KTI_ERROR_CNT, KTI_CREATED_TIMESTAMP, KTI_LAST_UPDATED_TIMESTAMP) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                RecordLog recordLog = recordLogs.get(i);
                ps.setString(1, recordLog.getTopicName());
                ps.setString(2, recordLog.getAppName());
                ps.setString(3, recordLog.getType());
                ps.setString(4, recordLog.getJsonString());
                ps.setString(5, recordLog.getStatus());
                ps.setString(6, recordLog.getErrorMessage());
                ps.setInt(7, recordLog.getErrorCNT());
                ps.setTimestamp(8, Timestamp.valueOf(recordLog.getCreatedTimestamp()));
                ps.setTimestamp(9, Timestamp.valueOf(recordLog.getUpdatedTimestamp()));
            }

            @Override
            public int getBatchSize() {
                return recordLogs.size();
            }
        });
    }

    public void batchUpdateRecords(List<RecordLog> recordLogs) {
        String sql = "UPDATE LOG_T " +
                     "SET KTI_TOPIC = ?, KTI_APP_NAME = ?, KTI_TYPE = ?, KTI_JSON = ?, " +
                     "KTI_STATUS = ?, KTI_ERROR_MESSAGE = ?, KTI_ERROR_CNT = ?, " +
                     "KTI_LAST_UPDATED_TIMESTAMP = ? " +
                     "WHERE KTI_ID = ?";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                RecordLog recordLog = recordLogs.get(i);
                ps.setString(1, recordLog.getTopicName());
                ps.setString(2, recordLog.getAppName());
                ps.setString(3, recordLog.getType());
                ps.setString(4, recordLog.getJsonString());
                ps.setString(5, recordLog.getStatus());
                ps.setString(6, recordLog.getErrorMessage());
                ps.setInt(7, recordLog.getErrorCNT());
                ps.setTimestamp(8, Timestamp.valueOf(recordLog.getUpdatedTimestamp()));
                ps.setLong(9, recordLog.getId());
            }

            @Override
            public int getBatchSize() {
                return recordLogs.size();
            }
        });
    }
}
