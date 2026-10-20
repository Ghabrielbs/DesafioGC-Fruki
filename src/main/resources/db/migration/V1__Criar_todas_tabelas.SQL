CREATE TABLE IF NOT EXISTS Cliente (
 id_cliente BIGINT PRIMARY KEY,
 cnpj VARCHAR(18),
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

CREATE TABLE IF NOT EXISTS Compra (
  id_compra INTEGER PRIMARY KEY,
  id_cliente INTEGER REFERENCES Cliente(id_cliente),
  id_item INTEGER REFERENCES Item(id_item),
  quantidade INTEGER NOT NULL CHECK (quantidade > 0),
  data TIMESTAMP NOT NULL,

  CONSTRAINT fk_compra_cliente FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
  CONSTRAINT fk_compra_item FOREIGN KEY (id_item) REFERENCES Item(id_item)
);



