CREATE TABLE public.employee (
     id SERIAL NOT NULL,
     username varchar NOT NULL,
     password varchar NOT NULL,
     role varchar NOT NULL,
     PRIMARY KEY (id)
);


CREATE TABLE public.task (
     id SERIAL NOT NULL,
     task_name varchar NOT NULL,
     PRIMARY KEY (id)
);


CREATE TABLE public.employee_task_estimates (
    employee_id integer NOT NULL,
    task_id integer NOT NULL,
    completion_time integer NOT NULL
);

ALTER TABLE public.employee_task_estimates
    ADD UNIQUE (employee_id, task_id);


CREATE TABLE public.order_table (
    id SERIAL NOT NULL,
    description varchar NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE public.task_queue (
   id SERIAL NOT NULL,
   task_id integer NOT NULL,
   employee_id integer,
   order_id integer NOT NULL,
   status varchar NOT NULL,
   feedback varchar,
   assignment_date timestamp without time zone NOT NULL,
   start_date timestamp without time zone,
   completion_date timestamp without time zone,
   PRIMARY KEY (id)
);


CREATE INDEX ON public.task_queue
    (task_id);
CREATE INDEX ON public.task_queue
    (employee_id);
CREATE INDEX ON public.task_queue
    (order_id);


ALTER TABLE public.employee_task_estimates ADD CONSTRAINT FK_employee_task_estimates__employee_id FOREIGN KEY (employee_id) REFERENCES public.employee(id);
ALTER TABLE public.employee_task_estimates ADD CONSTRAINT FK_employee_task_estimates__task_id FOREIGN KEY (task_id) REFERENCES public.task(id);
ALTER TABLE public.task_queue ADD CONSTRAINT FK_task_queue__task_id FOREIGN KEY (task_id) REFERENCES public.task(id);
ALTER TABLE public.task_queue ADD CONSTRAINT FK_task_queue__employee_id FOREIGN KEY (employee_id) REFERENCES public.employee(id);
ALTER TABLE public.task_queue ADD CONSTRAINT FK_task_queue__order_id FOREIGN KEY (order_id) REFERENCES public.order_table(id);