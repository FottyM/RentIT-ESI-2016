
insert into plant_inventory_entry (id, name, description, price) values (1, 'Mini excavator', '1.5 Tonne Mini excavator', 150);
insert into plant_inventory_entry (id, name, description, price) values (2, 'Mini excavator', '3 Tonne Mini excavator', 200);
insert into plant_inventory_entry (id, name, description, price) values (3, 'Midi excavator', '5 Tonne Midi excavator', 250);
insert into plant_inventory_entry (id, name, description, price) values (4, 'Midi excavator', '8 Tonne Midi excavator', 300);
insert into plant_inventory_entry (id, name, description, price) values (5, 'Maxi excavator', '15 Tonne Large excavator', 400);
insert into plant_inventory_entry (id, name, description, price) values (6, 'Maxi excavator', '20 Tonne Large excavator', 450);
insert into plant_inventory_entry (id, name, description, price) values (7, 'HS dumper', '1.5 Tonne Hi-Swivel Dumper', 150);
insert into plant_inventory_entry (id, name, description, price) values (8, 'FT dumper', '2 Tonne Front Tip Dumper', 180);
insert into plant_inventory_entry (id, name, description, price) values (9, 'FT dumper', '2 Tonne Front Tip Dumper', 200);
insert into plant_inventory_entry (id, name, description, price) values (10, 'FT dumper', '2 Tonne Front Tip Dumper', 300);
insert into plant_inventory_entry (id, name, description, price) values (11, 'FT dumper', '3 Tonne Front Tip Dumper', 400);
insert into plant_inventory_entry (id, name, description, price) values (12, 'Loader', 'Hewden Backhoe Loader', 200);
insert into plant_inventory_entry (id, name, description, price) values (13, 'D-Truck', '15 Tonne Articulating Dump Truck', 250);
insert into plant_inventory_entry (id, name, description, price) values (14, 'D-Truck', '30 Tonne Articulating Dump Truck', 300);

insert into plant_inventory_item (id, serial_number, condition, plant_info_id) values (1, 'EXC-1.5-123', 'SERVICEABLE', 1);
insert into plant_inventory_item (id, serial_number, condition, plant_info_id) values (2, 'EXC-1.5-124', 'SERVICEABLE', 1);
insert into plant_inventory_item (id, serial_number, condition, plant_info_id) values (3, 'EXC-3.0-125', 'REPAIRABLE', 2);
insert into plant_inventory_item (id, serial_number, condition, plant_info_id) values (4, 'EXC-5.0-123', 'SERVICEABLE', 3);
insert into plant_inventory_item (id, serial_number, condition, plant_info_id) values (5, 'EXC-5.0-123', 'SERVICEABLE', 3);
insert into plant_inventory_item (id, serial_number, condition, plant_info_id) values (6, 'EXC-8.0-123', 'SERVICEABLE', 4);
insert into plant_inventory_item (id, serial_number, condition, plant_info_id) values (7, 'EXC-15.0-123', 'SERVICEABLE', 5);
insert into plant_inventory_item (id, serial_number, condition, plant_info_id) values (8, 'EXC-20.0-123', 'SERVICEABLE', 6);

insert into plant_reservation (id, start_date, end_date, plant_id) values (1, '2016-04-04', '2016-04-06', 3);

insert into maintenance_task (id, type_of_work, reservation_id, price) values (1, 'CORRECTIVE', 1, 200);

insert into maintenance_task (id, type_of_work, price) values (100, 'CORRECTIVE', 200);
insert into maintenance_task (id, type_of_work, price) values (101, 'CORRECTIVE', 200);
insert into maintenance_task (id, type_of_work, price) values (102, 'CORRECTIVE', 100);
insert into maintenance_task (id, type_of_work, price) values (103, 'CORRECTIVE', 200);
insert into maintenance_task (id, type_of_work, price) values (104, 'CORRECTIVE', 200);
insert into maintenance_plan (id, year_of_action) values (1, 2014);
insert into maintenance_plan (id, year_of_action) values (2, 2015);
insert into maintenance_plan (id, year_of_action) values (3, 2016);
insert into maintenance_plan_tasks (maintenance_plan_id, task_id) values (1, 100);
insert into maintenance_plan_tasks (maintenance_plan_id, task_id) values (1, 101);
insert into maintenance_plan_tasks (maintenance_plan_id, task_id) values (1, 102);
insert into maintenance_plan_tasks (maintenance_plan_id, task_id) values (2, 103);
insert into maintenance_plan_tasks (maintenance_plan_id, task_id) values (2, 104);