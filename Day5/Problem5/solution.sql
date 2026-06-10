SELECT TOP 1
       bj.job_id,
       d.dept_name,
       bj.created_at
FROM background_jobs bj WITH (UPDLOCK, READPAST)
INNER JOIN departments d
ON bj.dept_id = d.dept_id
WHERE d.dept_name = 'Engineering'
  AND bj.status = 'PENDING'
ORDER BY bj.created_at;

CREATE INDEX idx_pending_jobs
ON background_jobs(status, dept_id, created_at);