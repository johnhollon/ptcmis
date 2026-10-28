UPDATE tissue_task_bottle b SET b.variety = (SELECT variety FROM tissue_task t WHERE t.id = b.id_Task),
b.variety_code = (SELECT variety_code FROM tissue_Task t WHERE t.id = b.id_Task);

UPDATE tissue_task_opp_group b SET b.variety_code = (SELECT variety_code FROM tissue_Task t WHERE t.id = b.id_Task);
