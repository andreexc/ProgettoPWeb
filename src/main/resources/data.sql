-- MERGE bypasses the problem with already inserted users
-- password already hashed for spring security
MERGE INTO users (username, password, enabled) KEY(username) VALUES
    ('admin#team_20', '$2a$10$3MbTTqit1YyKexct9rXZ1ul01nAuPXXNelXzzS8JDu4soOXGh9tdy', true),    -- ad_id_20
    ('basic#team_20', '$2a$10$en6a3fBSkRhdImnR6elo6eVOeoMqO8NB4iw/UKINbBVrHoutaZ4cG', true),    -- bs_id_20
    ('pro#team_20', '$2a$10$ipvX7uk/vKZj7j11ZCZj9.Av9JYeHICvqBNo22JTixUCrKnkhTdQq', true),      -- pv_id_20
    ('prova#1#team_20', '$2a$10$JWzsWpYWhcjvlHvCIL8y3ejieUGLCf.UFDbXab4pydoPyHOOlAOrK', true);  -- pr_id_20

MERGE INTO authorities (username, authority) KEY(username, authority) VALUES
    ('admin#team_20', 'ROLE_ADMIN'),
    ('basic#team_20', 'ROLE_USER_BASIC'),
    ('pro#team_20', 'ROLE_USER_PRO'),
    ('prova#1#team_20', 'ROLE_USER_PROVA');