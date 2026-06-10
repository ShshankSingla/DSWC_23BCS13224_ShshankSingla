WITH LatestPings AS (
    SELECT gp.ping_id,
           gp.rider_id,
           gp.latitude,
           gp.longitude,
           gp.recorded_at,
           ROW_NUMBER() OVER (
               PARTITION BY gp.rider_id
               ORDER BY gp.recorded_at DESC
           ) AS rn
    FROM gps_pings gp
)
SELECT r.rider_id,
       r.rider_name,
       r.bike_model,
       lp.latitude,
       lp.longitude,
       lp.recorded_at
FROM riders r
INNER JOIN LatestPings lp
ON r.rider_id = lp.rider_id
WHERE lp.rn = 1;

CREATE INDEX idx_gps_pings_rider_timestamp
ON gps_pings(rider_id, recorded_at DESC);