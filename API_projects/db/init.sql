BEGIN;

-- skills
CREATE TABLE skills (
  id_skill UUID PRIMARY KEY,
  name_skill VARCHAR(255) NOT NULL
);

-- subjects
CREATE TABLE subjects (
  id_subject UUID PRIMARY KEY,
  name_subject VARCHAR(255) NOT NULL
);

-- modules (agrupa níveis sob uma skill)
CREATE TABLE modules (
  id_module UUID PRIMARY KEY,
  id_skill UUID NOT NULL REFERENCES skills(id_skill) ON DELETE CASCADE,
  name_module VARCHAR(255) NOT NULL,
  order_num SMALLINT NOT NULL
);

-- levels (pertence a um module)
CREATE TABLE levels (
  id_level UUID PRIMARY KEY,
  id_module UUID NOT NULL REFERENCES modules(id_module) ON DELETE CASCADE,
  order_num SMALLINT NOT NULL,
  required_score FLOAT NOT NULL DEFAULT 0
);

-- questions
CREATE TABLE questions (
  id_question UUID PRIMARY KEY,
  id_level UUID NOT NULL REFERENCES levels(id_level) ON DELETE CASCADE,
  id_subject UUID NOT NULL REFERENCES subjects(id_subject) ON DELETE CASCADE,
  text VARCHAR(255) NOT NULL,
  option_a VARCHAR(255) NOT NULL,
  option_b VARCHAR(255) NOT NULL,
  option_c VARCHAR(255) NOT NULL,
  option_d VARCHAR(255) NOT NULL,
  option_e VARCHAR(255) NOT NULL,
  correct CHAR(1) NOT NULL
);

-- users: ID inteiro (para performance) + UUID exposto
CREATE TABLE users (
  id_user BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  uuid_user UUID NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  create_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  xp_points INTEGER NOT NULL DEFAULT 0,
  level INTEGER NOT NULL DEFAULT 1
);

-- cache de progresso por usuário + matéria (opcional, para performance)
CREATE TABLE user_subject_progress (
  user_id BIGINT NOT NULL REFERENCES users(id_user) ON DELETE CASCADE,
  subject_id UUID NOT NULL REFERENCES subjects(id_subject) ON DELETE CASCADE,
  unlocked_level SMALLINT NOT NULL DEFAULT 1,
  updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  PRIMARY KEY (user_id, subject_id)
);

-- answers (log de tentativas)
CREATE TABLE answers (
  id_answer UUID PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES users(id_user) ON DELETE CASCADE,
  question_id UUID NOT NULL REFERENCES questions(id_question) ON DELETE CASCADE,
  select_option CHAR(1) NOT NULL,
  is_correct BOOLEAN NOT NULL,
  attempt_count INTEGER NOT NULL DEFAULT 1,
  answered_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

-- flashcards
CREATE TABLE flashcards (
  id_flashcard UUID PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES users(id_user) ON DELETE CASCADE,
  concept VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  id_subject UUID NOT NULL REFERENCES subjects(id_subject) ON DELETE CASCADE,
  id_question UUID,
  create_date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  CONSTRAINT fk_flashcard_question FOREIGN KEY (id_question)
    REFERENCES questions (id_question) ON DELETE SET NULL
);

-- (opcional) tabela ranking — você pode manter ou descartar dependendo de como quer salvar meta-info
CREATE TABLE ranking (
  id_ranking UUID PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES users(id_user) ON DELETE CASCADE,
  label VARCHAR(50), -- ex: beginner/intermediate/advanced (se quiser)
  created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now()
);

-- índices úteis para performance
CREATE INDEX idx_answers_user_correct ON answers (user_id, is_correct);
CREATE INDEX idx_answers_question ON answers (question_id);
CREATE INDEX idx_questions_level ON questions (id_level);
CREATE INDEX idx_questions_subject ON questions (id_subject);
CREATE INDEX idx_users_uuid ON users (uuid_user);
CREATE INDEX idx_flashcards_user ON flashcards (user_id);

COMMIT;