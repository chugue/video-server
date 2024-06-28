
-- userKey를 보유하고 있는 구독회원
INSERT INTO key_tb (user_id, user_key, created_at) VALUES
(1, '1234', NOW()),
(2, '1234', NOW()),
(4, '1234', NOW()),
(6, '1234', NOW()),
(7, '1234', NOW()),
(9, '1234', NOW());

-- 비디오 요청을 처리할 테이블
INSERT INTO video_tb (id, title, video_path) VALUES
(1, '레디 플레이어 원', 'videolocation/ready_player_one/ready_player_one.mpd'),
(2, '메이즈 러너', 'videolocation/maze_runner/main-maze_runner.mpd'),
(3, '블레이드러너2049', 'videolocation/blade_runner/blade_runner.mpd'),
(4, '인터스텔라', 'videolocation/interstella/interstella.mpd'),
(5, '아틀라스', 'videolocation/atlas/atlas.mpd'),
(6, '센과 치히로의 행방불명', 'videolocation/chihiro/chihiro.mpd'),
(7, '존윅', 'videolocation/john_wick/john_wick.mpd'),
(8, '고양이의 보은', 'videolocation/thecatreturns/thecatreturns.mpd'),
(9, '어느 일란성 세 쌍둥이의 재회', 'videolocation/three_twins/three_twins.mpd'),
(10, '도라에몽: 스탠바이미', 'videolocation/doraemon/doraemon.mpd');
INSERT INTO video_tb (id, title, video_path) VALUES
(11, '극장판 포켓몬스터: 너로 정했다!', 'videolocation/poketmonster/poketmonster.mpd'),
(12, '몬스터 주식회사', 'videolocation/monster_inc/monster_inc.mpd'),
(13, '나 홀로 집에', 'videolocation/home_alone/home_alone.mpd'),
(14, '악마는 프라다를 입는다', 'videolocation/devil_wears_prada/devil_wears_prada.mpd'),
(15, '행오버', 'videolocation/hangover/hangover.mpd'),
(16, '화이트칙스', 'videolocation/white_chicks/white_chicks.mpd'),
(17, '찰리와 초콜릿 공장', 'videolocation/charlie_chocolate/charlie_chocolate.mpd'),
(18, '유전', 'videolocation/hereditary/hereditary.mpd'),
(19, '부산행', 'videolocation/train_to_busan/train_to_busan.mpd'),
(20, '랑종', 'videolocation/rangjong/rangjong.mpd');
INSERT INTO video_tb (id, title, video_path) VALUES
(21, '월드 워 Z', 'videolocation/wolrd_war_z/wolrd_war_z.mpd'),
(22, '조커', 'videolocation/joker/joker.mpd'),
(23, '스마트폰을 떨어뜨렸을 뿐인데', 'videolocation/smartphone_drop/smartphone_drop.mpd'),
(24, '기생수', 'videolocation/parasites/parasites.mpd'),
(25, '나의 문어 선생님', 'videolocation/octopus_teacher/octopus_teacher.mpd'),
(26, '다크나이트', 'videolocation/darknight/darknight.mpd'),
(27, '도둑들', 'videolocation/thieves/thieves.mpd'),
(28, '언노운: 우주를 보는 타임머신', 'videolocation/unknown/unknown.mpd'),
(29, '살인자의 기억법', 'videolocation/killers_memory/killers_memory.mpd'),
(30, '올드보이', 'videolocation/oldboy/oldboy.mpd');
INSERT INTO video_tb (id, title, video_path) VALUES
(21, '월드 워 Z', 'videolocation/wolrd_war_z/wolrd_war_z.mpd'),
(22, '조커', 'videolocation/joker/joker.mpd'),
(23, '스마트폰을 떨어뜨렸을 뿐인데', 'videolocation/smartphone_drop/smartphone_drop.mpd'),
(24, '기생수', 'videolocation/parasites/parasites.mpd'),
(25, '나의 문어 선생님', 'videolocation/octopus_teacher/octopus_teacher.mpd'),
(26, '다크나이트', 'videolocation/darknight/darknight.mpd'),
(27, '도둑들', 'videolocation/thieves/thieves.mpd'),
(28, '언노운: 우주를 보는 타임머신', 'videolocation/unknown/unknown.mpd'),
(29, '살인자의 기억법', 'videolocation/killers_memory/killers_memory.mpd'),
(30, '올드보이', 'videolocation/oldboy/oldboy.mpd');
INSERT INTO video_tb (id, title, video_path) VALUES
(41, '쥬만지', 'videolocation/jumanji/jumanji.mpd'),
(42, '러시아워', 'videolocation/rush_hour/rush_hour.mpd'),
(43, '나는 신이다: 신이 배신한 사람들', 'videolocation/i_am_god/i_am_god.mpd'),
(44, '고양이는 왜 고양이일까?', 'videolocation/why_cat/why_cat.mpd'),
(45, '오펜하이머', 'videolocation/oppenheimer/oppenheimer.mpd');

