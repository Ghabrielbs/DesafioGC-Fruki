CREATE TABLE IF NOT EXISTS Cliente (
 id_cliente INTEGER PRIMARY KEY,
 cod_segmento TEXT,
 cod_subsegmento TEXT,
 cod_situacao_credito TEXT,
 estado TEXT,
 situacao_cliente TEXT,
 situacao_credito TEXT,
 endereco TEXT,
 bairro TEXT,
 cidade TEXT,
 cep TEXT
);

CREATE TABLE IF NOT EXISTS Item (
 id_item INTEGER PRIMARY KEY,
 cod_familia TEXT,
 cod_marca TEXT,
 origem TEXT,
 cod_desc_familia TEXT,
 sabor TEXT,
 sabor_tipo TEXT
);

CREATE TABLE IF NOT EXISTS Tabela_Preco (
 id_tabela_preco INTEGER PRIMARY KEY,
 tabela_ativa TEXT,
 id_item INTEGER,
 preco_venda NUMERIC(12,2),
 preco_minimo NUMERIC(12,2)
);

