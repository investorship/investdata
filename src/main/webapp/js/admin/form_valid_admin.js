/** 封装表单验证js* */

//资产负债表表单
function balFormValid() {
	$(document).ready(function() {
		$("#balForm").validate({
			rules : {
				"balanceSheet.code" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16 
				},
				"balanceSheet.year" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.noteRecable" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.advCustomers" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.accPayable" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.constrInPro" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.lntangAssets" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.goodwill" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.shortTermLoans" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.notePayable" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.capitalSurplus" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.surplusReserve" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.longTermLoans" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.boundsPayable" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.goodsStart" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.goodsEnd" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.cash" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.currLiab" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.currLiab" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.currLiabNon" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.currLiabNon" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.liquidAssetsStart" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.liquidAssetsEnd" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.totalAssStart" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.totalAssEnd" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.shareHolderStart" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.shareHolderEnd" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.fixedAssetsStart" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.fixedAssetsEnd" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.accRecableStart" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.accRecableEnd" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.debitWithinYear" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.lntangAssetsAmortize" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.longAccPayable" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.tradAssets" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.totalLiabStart" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.totalLiabEnd" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"balanceSheet.flag" : {
					number : true,
					required : true,
					range:[0,1]
				}
			}
		});
	});
}


//股票表单
function stockFormValid() {
	$(document).ready(function() {
		$("#stockForm").validate({
			rules : {
				"stock.code" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 10 
				},
				"stock.name" : {
					required : true
				},
				"stock.shortName" : {
					required : true,
					minlength : 2,
					maxlength : 10
				},
				"stock.market" : {
					required : true
				},
				"stock.ipoTime" : {
					date:true,
					required : true
				},
				"stock.ipoStocks" : {
					digits:true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"stock.category" : {
					required : true
				},
				"stock.issuedPE" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"stock.issuedPrice" : {
					number : true,
					required : true,
					minlength : 2,
					maxlength : 16
				},
				"stock.legaler" : {
					required : true
				},
				"stock.phone" : {
					required : true
				},
				"stock.address" : {
					required : true
				},
				"stock.compyWebsite" : {
					url:true,
					required : true
				},
				"stock.reportAddress" : {
					required : true
				},
				"stock.flag" : {
					number : true,
					required : true,
					range:[0,1]
				}
			}
		});
	});
}





//财务指标表单
function financeIndexFormValid() {
	$(document).ready(function() {
		$("#financeIndexForm").validate({
			rules : {
				"financeIndexInfo.name" : {
					required : true
				},
				"financeIndexInfo.pid" : {
					required : true
				},
				"financeIndexInfo.action" : {
					required : true
				},
				"financeIndexInfo.flag" : {
					required : true
				}
			}
		});
	});
}


































