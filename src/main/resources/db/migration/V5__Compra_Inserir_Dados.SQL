INSERT INTO Compra (id_compra, id_cliente, id_item, quantidade, data) VALUES
  -- Cliente Ativo (121643): Varejo e volume médio
  (1,  121643, 1,     6,   '2024-01-15 09:30:00'),  -- 6 un. Refrigerante PET
  (2,  121643, 19377, 12,  '2024-01-20 14:15:22'),  -- 12 un. (pack) Energético Lata
  (3,  121643, 22479, 24,  '2024-02-01 10:00:00'),  -- 24 un. (fardo) Água

  -- Cliente Ativo (141268): Pedido maior (atacado/distribuição)
  (4,  141268, 22479, 120, '2024-02-10 11:20:00'),  -- 120 un. Água
  (5,  141268, 28998, 48,  '2024-02-10 11:22:15'),  -- 48 un. Cerveja Lata
  (6,  141268, 13,    30,  '2024-02-18 16:45:10'),  -- 30 un. Refrigerante PET

  -- Cliente Ativo (139198): Compras moderadas
  (7,  139198, 25,    10,  '2024-03-05 08:12:40'),  -- 10 un. Refrigerante Vidro
  (8,  139198, 20279, 15,  '2024-03-22 17:05:00'),  -- 15 un. Suco

  -- Cliente Ativo (160075): Compras menores
  (9,  160075, 20279, 4,   '2024-04-02 13:40:11'),  -- 4 un. Suco
  (10, 160075, 18057, 8,   '2024-04-15 15:30:00'),  -- 8 un. Refrigerante PET

  -- Clientes Inativos/Suspensos (110569 e 45127): Histórico prévio à suspensão
  (11, 110569, 1,     20,  '2023-11-10 14:00:00'),
  (12, 110569, 19382, 6,   '2023-11-25 18:20:00'),
  (13, 45127,  28998, 60,  '2023-12-05 12:10:30'),
  (14, 45127,  29484, 12,  '2023-12-20 09:45:00'),

  -- Cliente Ativo (37965): Compras recentes para testes operacionais
  (15, 37965, 1,      2,   '2024-05-01 10:15:00'),
  (16, 37965, 13,     18,  '2024-05-02 11:30:00'),
  (17, 37965, 22479,  50,  '2024-05-03 16:00:00');

