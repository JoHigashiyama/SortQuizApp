-- ユーザーテーブル
CREATE TABLE users (
                       user_id INTEGER AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       best_score INTEGER DEFAULT 0,
                       total_score INTEGER DEFAULT 0,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- スコアテーブル
CREATE TABLE scores (
                        score_id INTEGER AUTO_INCREMENT PRIMARY KEY,
                        user_id INTEGER NOT NULL,
                        score INTEGER NOT NULL,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_scores_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- クイズ問題テーブル
CREATE TABLE quizzes (
                         quiz_id INTEGER AUTO_INCREMENT PRIMARY KEY,
                         content VARCHAR(255) NOT NULL,   -- 選択肢（出来事名など）
                         happen_year INT NOT NULL,               -- 年代
                         description VARCHAR(1000),       -- 解説
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
