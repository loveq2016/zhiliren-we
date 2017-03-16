/*applyapproval_list*/
select * from(
select uo.orderstype,ti.id,ti.PNR,ti.orderPnrStatus,ti.salespricesum,uo.ordersnum,ti.optime,p.purpose,
p.proposer,ci.shortName,p.fundType,p.payFees,p.payCurrency,p.isInvioce,p.approveTime,p.approveResult,p.id as 'usingId'
,uo.id as 'orderId',p.companyId,u.userName
FROM t_pnr_info  ti
LEFT JOIN t_order_customneed oc on ti.needid=oc.id
LEFT JOIN t_up_order uo on oc.ordernum =uo.id
LEFT JOIN t_pay_pnr pp on pp.pnrId=ti.id
LEFT JOIN t_pay p on p.id = pp.payId
LEFT JOIN t_customer_info ci on ci.id=uo.userid
LEFT JOIN t_user u on u.id=p.proposer
) temp
$condition
order by optime desc






