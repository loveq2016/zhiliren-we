/*company_dicttype_list*/
SELECT
	cd.id,
	cd.comId,
	c.comName,
	cd.comTypeCode,
	cd.comTypeName,
	cd.`status`,
	cd.createTime,
	cd.updateTime,
	cd.remark
FROM
	t_company_dicttype cd
LEFT JOIN t_company c ON c.id = cd.comId
$condition

/*company_dictinfo_list*/
SELECT
	cmd.id,
	cmd.dictTypeId,
	cty.comTypeName,
	cmd.comId,
	c.comName,
	cmd.comTypeCode,
	cmd.comDdictCode,
	cmd.comDictName,
	cmd.`status`,
	cmd.quanPin,
	cmd.jianPin,
	cmd.createTime,
	cmd.updateTime,
	cmd.remark
FROM
	t_company_dictinfo cmd
INNER JOIN t_company_dicttype cty ON cty.comTypeCode = cmd.comTypeCode
LEFT JOIN t_company c ON c.id = cmd.comId
$condition

/*company_select_dicttypename*/
SELECT
	cty.id,
	cty.comId,
	cty.comTypeCode,
	cty.comTypeName,
	cty.`status`,
	cty.createTime,
	cty.updateTime,
	cty.remark
FROM
	t_company_dicttype cty
LEFT JOIN t_company c ON c.id=cty.comId
$condition

/*company_dict_updateinfo*/
SELECT
	cin.id,
	cin.dictTypeId,
	cin.comId,
	cin.comTypeCode,
	cty.comTypeName,
	cin.comDdictCode,
	cin.comDictName,
	cin.`status`,
	cin.quanPin,
	cin.jianPin,
	cin.createTime,
	cin.updateTime,
	cin.remark
FROM
	t_company_dictinfo cin
LEFT JOIN t_company c ON c.id = cin.comId
LEFT JOIN t_company_dicttype cty ON cty.comTypeCode = cin.comTypeCode
$condition