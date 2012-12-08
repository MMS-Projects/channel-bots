CREATE TABLE sqlite_sequence(name,seq);
CREATE TABLE "pb_triggers" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
    "type" TEXT,
    "data" TEXT,
    "channel" TEXT
);
CREATE TABLE "pb_actions" (
    "id" INTEGER PRIMARY KEY AUTOINCREMENT,
    "trigger_id" INTEGER,
    "type" TEXT,
    "data" TEXT
);
