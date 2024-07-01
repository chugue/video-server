
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
(2, '메이즈 러너', 'videolocation/maze_runner/maze_runner.mpd'),
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
(22, '곤지암', 'videolocation/gonziam/gonziam.mpd'),
(23, '조커', 'videolocation/joker/joker.mpd'),
(24, '스마트폰을 떨어뜨렸을 뿐인데', 'videolocation/smartphone_drop/smartphone_drop.mpd'),
(25, '기생수', 'videolocation/parasites/parasites.mpd'),
(26, '나의 문어 선생님', 'videolocation/octopus_teacher/octopus_teacher.mpd'),
(27, '다크나이트', 'videolocation/darknight/darknight.mpd'),
(28, '도둑들', 'videolocation/thieves/thieves.mpd'),
(29, '언노운: 우주를 보는 타임머신', 'videolocation/unknown/unknown.mpd'),
(30, '살인자의 기억법', 'videolocation/killers_memory/killers_memory.mpd');
INSERT INTO video_tb (id, title, video_path) VALUES
(31, '올드보이', 'videolocation/oldboy/oldboy.mpd'),
(32, '헤어질 결심', 'videolocation/decision_to_leave/decision_to_leave.mpd'),
(33, '어바웃 타임', 'videolocation/about_time/about_time.mpd'),
(34, '남자가 사랑할때', 'videolocation/when_a_man_loves/when_a_man_loves.mpd'),
(35, '그 시절, 우리가 좋아했던 소녀', 'videolocation/when_we_like_a_girl/when_we_like_a_girl.mpd'),
(36, '싱글 인 서울', 'videolocation/single_in_seoul/single_in_seoul.mpd'),
(37, '선과 악의 학교', 'videolocation/school_of_evil_good/school_of_evil_good.mpd'),
(38, '조선명탐정', 'videolocation/chosun_detector/chosun_detector.mpd'),
(39, '살인의 추억', 'videolocation/memories_murder/memories_murder.mpd'),
(40, '트와일라잇', 'videolocation/twilight/twilight.mpd');
INSERT INTO video_tb (id, title, video_path) VALUES
(41, '마스크', 'videolocation/mask/mask.mpd'),
(42, '쥬만지', 'videolocation/jumanji/jumanji.mpd'),
(43, '러시아워', 'videolocation/rush_hour/rush_hour_encrypted.mpd'),
(44, '나는 신이다: 신이 배신한 사람들', 'videolocation/i_am_god/i_am_god.mpd'),
(45, '고양이는 왜 고양이일까?', 'videolocation/why_cat/why_cat.mpd'),
(46, '오펜하이머', 'videolocation/oppenheimer/oppenheimer.mpd');