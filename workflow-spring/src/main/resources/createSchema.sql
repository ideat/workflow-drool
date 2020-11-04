CREATE SCHEMA if not exists workflow
   AUTHORIZATION postgres;

CREATE SCHEMA if not exists comercial
    AUTHORIZATION postgres;

CREATE TABLE if not exists public.office (
   id uuid NOT NULL,
   internal_code int,
   name character varying(2044),
   address character varying(2044) NOT NULL,
   phone character varying(2044) NOT NULL,
   city character varying(2044) NOT NULL,
   province character varying(2044) NOT NULL,
   type_office character varying(2044) NOT NULL,
   id_root uuid NOT NULL,
   signatorie jsonb NOT NULL default '[]',
   CONSTRAINT pk_public_office_id PRIMARY KEY (id),
   CONSTRAINT unique_public_internal_code UNIQUE (internal_code)
);

CREATE TABLE if not exists public.users(
                             id uuid NOT NULL ,
                             id_user character varying(20),
                             email character varying(50),
                             password character varying(2044),
                             date_update_password date,
                             names character varying(500) not null ,
                             num_days_validity int not null ,
                             state character varying(20) not null ,
                             rol character varying(100),
                             code_office int not null ,
                             date_creation date not null ,
                             position character varying not null ,
                             supervisor character varying not null ,
                             scope character varying,
                             CONSTRAINT users_pkey PRIMARY KEY (id),
                             CONSTRAINT unique_public_id_user UNIQUE (id_user)

);

CREATE TABLE if not exists public.rol(
  id uuid not null ,
  name character varying(50) not null ,
  description character varying default '',
  options jsonb default '[]' not null ,
  states jsonb default '[]' not null ,
  scope character varying(30) not null default 'LOCAL',
  constraint rol_pkey PRIMARY KEY (id),
  CONSTRAINT unique_public_name_rol UNIQUE (name)
);

CREATE TABLE if not exists workflow.parameter (
     id uuid NOT NULL,
     category character varying(2044) NOT NULL,
     value character varying(2044) NOT NULL,
     description character varying(2044) NOT NULL,
     CONSTRAINT parameter_pkey PRIMARY KEY (id),
     CONSTRAINT unique_workflow_category_value UNIQUE (category,value)
);

create sequence if not exists number_applicant_id_seq;

CREATE TABLE if not exists workflow.applicant(
    id uuid NOT NULL ,
    external_system_code integer default 0,
    number_applicant integer default nextval('workflow.number_applicant_id_seq'::regclass),
    first_name character varying(200) not null ,
    second_name character varying(200),
    married_last_name character varying(200),
    last_name character varying(200),
    mother_last_name character varying(200),
    home_address character varying(1000) not null ,
    home_address_reference character varying default '',
    id_card character varying(20) not null ,
    id_card_expedition  character varying(5),
    id_card_complement character varying(5),
    type_id_card character varying(50),
    date_expiration_id_card date not null ,
    civil_status character varying(50) not null ,
    dependent_number int not null ,
    birthdate date not null ,
    gender character varying(20),
    profession character varying(100),
    nationality character varying(100),
    caedec character varying (20),
    register_date date not null ,
    cellphone character varying (50),
    homephone character varying(50),
    workphone character varying(50),
    workcellphone character varying(50),
    workaddress character varying(1000),
    name_company_work character varying(200),
    position character varying(100),
    workingtime int ,
    nit character varying(20),
    id_office int not null,
    login_user character varying(50) not null,
    number_applicant_spouse integer default 0,
    type_person character varying(20) not null default '',
    society_type character varying(50) default '',
    initials character varying(50) default '',
    antiquity_area integer default 0,
    number_employees integer default 0,
    webpage character varying default '',
    constitution_date date,
    comercial_number character varying(50) default '',
    city character varying default '',
    block character varying default '',
    type_home character varying default '',
    customer_from date,
    saving_account character varying default '0',
    workzone character varying default '',
    CONSTRAINT applicant_pkey PRIMARY KEY (id),
    CONSTRAINT unique_workflow_id_card UNIQUE (id_card,id_card_expedition)
);

create sequence if not exists number_request_id_seq;

CREATE TABLE if not exists workflow.creditrequest(
    id uuid NOT NULL ,
    number_request integer default nextval('workflow.number_request_id_seq'::regclass),
    amount numeric(15,2) not null ,
    rate_interest numeric(5,2) not null ,
    currency character varying(5) not null ,
    term int not null ,
    type_term character varying(30),
    payment_period int not null ,
    fixed_day int not null default 0,
    type_fee character varying(30) not null,
    base_interest_rate numeric(5,2),
    init_period_base_rate int default 0,
    caedec character varying(10) not null,
    state character varying(30) not null ,
    request_Date date not null ,
    destination character varying(2000) not null ,
    charge jsonb not null default '[]',
    linkup jsonb not null default '[]',
    login_user character varying(50) not null ,
    id_office int not null,
    location character varying(100),
    type_credit character varying not null ,
    product_credit character varying not null ,
    state_history jsonb not null default '[]',
    hours_spend int,
    days_spend int,
    number_credit int,
    type_guarantee character varying,
    company_size_indicator jsonb default '[]',
    CONSTRAINT creditrequest_pkey PRIMARY KEY (id),
    CONSTRAINT unique_workflow_number_request UNIQUE (number_request)

);

create table if not exists workflow.creditrequest_applicant(
    id uuid not null ,
    number_applicant int not null,
    number_request int not null ,
    type_relation character varying(20) not null,
    CONSTRAINT creditrequest_applicant_pkey PRIMARY KEY (id),
    CONSTRAINT unique_workflow_creditrequest_applicant UNIQUE (number_applicant,number_request, type_relation)

);

create table if not exists workflow.template_forms(
    id uuid not null ,
    name character varying not null ,
    fields_structure jsonb not null default '[]',
    category character varying,
    CONSTRAINT template_forms_pkey PRIMARY KEY (id),
    CONSTRAINT unique_workflow_template_forms UNIQUE (name)

);

create table if not exists workflow.patrimonial_statement(
    id uuid not null ,
    id_credit_request_applicant uuid not null ,
    field_text1 character varying default '',
    field_text2 character varying default '',
    field_text3 character varying default '',
    field_text4 character varying default '',
    field_text5 character varying default '',
    field_text6 character varying default '',
    field_text7 character varying default '',
    field_text8 character varying default '',
    field_text9 character varying default '',
    field_text10 character varying default '',
    field_text11 character varying default '',
    field_text12 character varying default '',
    field_text13 character varying default '',
    field_text14 character varying default '',
    field_text15 character varying default '',
    field_text16 character varying default '',
    field_selection1 character varying default '',
    field_selection2 character varying default '',
    field_integer1 integer default 0,
    field_boolean1 character varying(2) default 'NO',
    field_boolean2 character varying(2) default 'NO',
    field_boolean3 character varying(2) default 'NO',
    field_double1 double precision default 0.0,
    field_double2 double precision default 0.0,
    field_double3 double precision default 0.0,
    field_double4 double precision default 0.0,
    field_double5 double precision default 0.0,
    field_date1 date ,
    field_date2 date,
    field_date3 date,
    field_date4 date,
    category character varying not null ,
    element_category character varying not null ,
    CONSTRAINT patrimonial_statement_pkey PRIMARY KEY (id)
);

create table if not exists workflow.payment_plan(
    id uuid not null ,
    number_request int  not null ,
    quota_number int  not null ,
    payment_date date not null ,
    capital numeric(10,2) not null default 0.0,
    interest numeric(10,2) not null default 0.0,
    secure_charge numeric(10,2) not null default  0.0,
    other_charge numeric(10,2) not null default 0.0,
    fee numeric(10,2) not null default 0.0,
    residue numeric(10,2) not null default 0.0,
    CONSTRAINT payment_plan_pkey PRIMARY KEY (id),
    CONSTRAINT unique_workflow_payment_plan UNIQUE (number_request,quota_number)
);

create table if not exists workflow.workup_review(
    id uuid not null,
    number_request integer,
    category character varying not null,
    item character varying not null,
    result character varying not null,
    date_register date,
    CONSTRAINT workup_preview_pkey PRIMARY KEY (id)
);

create table if not exists workflow.template_observation(
    id uuid not null ,
    category character varying(100) not null ,
    condition character varying not null ,
    observation character varying not null default '',
    answer character varying default '',
    task character varying not null default '',
    register_date date not null ,
    state character varying(20) not null ,
    sequence int not null ,
    CONSTRAINT  template_observation_pkey PRIMARY KEY (id)
);

create table if not exists workflow.observation(
    id uuid not null ,
    number_request int not null ,
    number_applicant int not null ,
    observations jsonb not null default '[]',
    task character varying not null ,
    constraint observation_pkey primary key (id)
);

create table if not exists workflow.credit_resolution(
    id uuid not null,
    number_request int not null,
    reciprocity numeric(10,2) default 0.0,
    sector character varying not null ,
    item character varying not null ,
    credit_object character varying not null default '',
    conclusion character varying not null default '',
    exceptions jsonb not null default '[]',
    type_resolution character varying not null default '',
    applicant_rating character varying(30) not null default '',
    amortization_description character varying not null,
    direct_indirect_debts jsonb not null default '[]',
    creation_date date not null ,
    constraint credit_resolution_pkey primary key (id),
    CONSTRAINT unique_credidt_resolution UNIQUE (number_request)
);

create table if not exists workflow.legal_information(
    id uuid not null,
    number_request int not null ,
    registration character varying not null default '',
    registration_seat jsonb not null default '[]',
    property_type character varying not null default '',
    location character varying not null default '',
    surface numeric(10,2) not null default 0.0,
    north character varying not null default '',
    south character varying not null default '',
    east character varying not null default '',
    west character varying not null default '',
    owners jsonb not null default '[]',
    type_title character varying not null default '',
    public_deed character varying not null default '',
    date_deed date not null ,
    given_by character varying not null default '',
    documents_submitted jsonb not null default '[]',
--     observations jsonb not null default '[]',
--     missing_documentation jsonb not null default '[]',
--     contract_requirements jsonb not null default '[]',
    conclusion character varying not null default '',
--     clarifications jsonb not null default '[]',
    details jsonb not null default '[]',
    creation_date date not null ,
    report_number character varying(20) not null ,
    create_by character varying(20) not null ,
    constraint legal_information_pkey primary key (id),
    constraint  unique_legal_information unique (number_request,report_number)
);

create table if not exists workflow.workflow_product(
    id uuid not null ,
    code_product_credit character varying not null ,
    request_stage jsonb not null default '[]',
    constraint workflow_product_pkey primary key (id),
    constraint unique_workflow_product unique (code_product_credit)
);

create table if not exists workflow.stage_history(
    id uuid not null ,
    number_request int not null ,
    stage character varying not null ,
    start_datetime timestamp not null ,
    state character varying not null ,
    next_state character varying default '',
    finished_state character varying,
    init_datetime timestamp,
    user_task character varying,
    observation character varying,
    answer character varying,
    comes_from character varying not null ,
    constraint stage_history_pkey primary key (id)
);

create table  if not exists workflow.mail(
    id uuid not null ,
    number_request int not null ,
    sendDate timestamp not null ,
    mail_from character varying not null ,
    mail_to character varying not null ,
    mail_cc character varying,
    mail_subject character varying not null ,
    mail_content character varying not null ,
    content_type character varying not null default 'text/plain',
    attachments character varying,
    constraint mail_pkey primary key (id)
);

create table if not exists workflow.template_contract(
    id uuid not null ,
    file_name character varying not null ,
    path_contract character varying not null ,
    detail character varying default '',
    active character varying(5) not null,
    constraint template_contract_pkey primary key (id)
);

create table if not exists workflow.cash_flow(
    id uuid not null,
    number_request int not null ,
    items jsonb not null default '[]',
    description character varying default ''
);

create table if not exists workflow.exceptions(
    id uuid not null ,
    internal_code character varying not null ,
    description character varying not null ,
    type_exception character varying not null ,
    limit_time int not null ,
    state character varying not null ,
    risk_type character varying not null ,
    constraint exceptions_pkey primary key (id),
    constraint unique_internal_code unique (internal_code)
);

create table if not exists workflow.authorizer
(
    id                 uuid              not null,
    login_user          character varying not null,
    maximum_amount_bs  numeric(10, 2)    not null default 0,
    minimum_amount_bs  numeric(10, 2)    not null default 0,
    maximum_amount_sus numeric(10, 2)    not null default 0,
    minimum_amount_sus numeric(10, 2)    not null default 0,
    scope              character varying not null,
    state              character varying not null,
    constraint authorizer_pkey primary key (id)
);

create table if not exists workflow.exceptions_creditrequest(
    id uuid not null ,
    code_exception character varying(10) not null ,
    number_request int not null ,
    state character varying not null ,
    register date not null ,
    justification character varying not null ,
    status_review jsonb default '[]',
    risk_type character varying not null ,
    constraint exceptions_creditrequest_pkey primary key (id)
);

create table if not exists comercial.client(
    id uuid not null ,
    names character varying not null ,
    lastNames character varying not null ,
    id_card character varying not null ,
    extension character varying(5) not null ,
    register_date date not null ,
    type_person character varying(50) not null ,
    tye_client character varying(50) not null ,
    size character varying(20) not null ,
    caedec character varying(10) not null ,
    cell_phone character varying(50),
    home_phone character varying(50),
    email character varying,
    address_home character varying,
    address_work character varying,
    login_user character varying not null,
    constraint client_pkey primary key (id),
    constraint unique_id_card_and_extension unique (id_card,extension)
);

create table if not exists workflow.exchange_rate(
    id uuid not null ,
    currency character varying(10) not null ,
    exchange numeric(10,2) not null default 0,
    start_validity date not null ,
    end_validity date,
    constraint exchange_rate_pk primary key (id),
    constraint unique_validity_date unique(start_validity,end_validity)
);

create table if not exists workflow.contract_variable(
    id uuid not null ,
    type_variable character varying(20) not null ,
    name character varying not null ,
    variable character varying not null ,
    constraint contract_variable_pkey primary key (id),
    constraint unique_name_variable unique(name)
);

create table if not exists workflow.contract(
  id uuid not null ,
  number_request integer not null ,
  date_contract date not null ,
  denomination_debtor character varying not null ,
  denomination_guarantor character varying not null ,
  denomination_creditor character varying not null ,
  file_name character varying not null ,
  description character varying default '',
  path_contract character varying not null,
  path_template character varying not null ,
  constraint contract_pkey primary key (id),
  constraint unique_number_request unique (number_request)
);

create table if not exists public.city_provinces(
    id uuid not null ,
    city character varying not null,
    provinces jsonb not null default '[]',
    external_code int,
    constraint  city_provinces_pkey primary key (id),
    constraint unique_city unique (city)
);

create table if not exists workflow.type_credit(
    id uuid not null ,
    description character varying not null ,
    external_code character varying,
    product_type_credit jsonb not null default '[]',
    object_credit jsonb not null default '[]',
    constraint type_credit_id primary key (id)
);

