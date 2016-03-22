COPY
(
    SELECT * from intencao_voto iv
inner join candidato ca on ca.id = iv.id_candidato
inner join servidor s on s.id = ca.id_servidor
inner join pessoa p on p.id = s.id_pessoa
)
TO 'C:/Users/daniel/Desktop/teste.txt'
DELIMITER ';'
CSV HEADER