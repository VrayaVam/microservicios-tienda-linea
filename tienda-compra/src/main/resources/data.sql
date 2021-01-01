INSERT INTO tbl_facturas (id, numero_factura, descripion, id_Cliente, fecha_creacion, estado) VALUES(1, '001', 'Detalles de la oficina de factura', 1, NOW(),'CREATED');

INSERT INTO tbl_factura_detalles (id_factura, id_producto, cantidad, precio) VALUES (1, 1, 1, 178.89);
INSERT INTO tbl_factura_detalles (id_factura, id_producto, cantidad, precio) VALUES (1, 2, 2, 12.5);
INSERT INTO tbl_factura_detalles (id_factura, id_producto, cantidad, precio) VALUES (1, 3, 1, 40.06);