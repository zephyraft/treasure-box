# 查看lfs规则
lfs:
	git lfs track

# 新增lfs规则
lfsa:
	git lfs track "*.$(suffix)"

# sed vs. gsed
# mac版的sed跟标准的gnu sed不同，此处改用gsed
# brew install gnu-sed
# https://www.gnu.org/software/sed/manual/sed.html
# -i表示编辑原文件 4i\表示 第4行前

# 新增recipe
# make ra recipe=xxx
ra:
	mkdir treasure-box-$(recipe)
	touch treasure-box-$(recipe)/README.md
	touch treasure-box-$(recipe)/build.gradle.kts
	# gsed -i '4i\    "treasure-box-$(recipe)",' settings.gradle.kts
	sed -i '4i\    "treasure-box-$(recipe)",' settings.gradle.kts