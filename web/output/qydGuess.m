clear;
close all

costCol = 1:11;
effortCol = 18;
pureNumWholeWeek0 = xlsread('analyze.xls');
normalizedPureNumberWholeWeek0 = mapminmax(pureNumWholeWeek0',0,1)';
sumWeeklyEffort0 = sum(normalizedPureNumberWholeWeek0(:,effortCol),2);
sumWeeklyCost0 = sum(normalizedPureNumberWholeWeek0(:,costCol),2);
productivity0 = sumWeeklyEffort0./sumWeeklyCost0;
figure('name','whole lifecycle')
hold on;
plot(productivity0,'-x')
%plot(normalizedPureNumberWholeWeek0(:,13))

pureNumWholeWeek4 = xlsread('analyzeV4.xls');
pureNumWholeWeek4(end+1,:) = 0;
normalizedPureNumberWholeWeek4 = mapminmax(pureNumWholeWeek4',0,1)';
sumWeeklyEffort4 = sum(normalizedPureNumberWholeWeek4(:,effortCol),2);
sumWeeklyCost4 = sum(normalizedPureNumberWholeWeek4(:,costCol),2);
productivity4 = sumWeeklyEffort4./sumWeeklyCost4;
traditionalProductivity4 = normalizedPureNumberWholeWeek4(:,13)./normalizedPureNumberWholeWeek4(:,18);

pureNumWholeWeek5 = xlsread('analyzeV5.xls');
pureNumWholeWeek5(end+1,:) = 0;
normalizedPureNumberWholeWeek5 = mapminmax(pureNumWholeWeek5',0,1)';
sumWeeklyEffort5 = sum(normalizedPureNumberWholeWeek5(:,effortCol),2);
sumWeeklyCost5 = sum(normalizedPureNumberWholeWeek5(:,costCol),2);
productivity5 = sumWeeklyEffort5./sumWeeklyCost5;
traditionalProductivity5 = normalizedPureNumberWholeWeek5(:,13)./normalizedPureNumberWholeWeek5(:,18);

pureNumWholeWeek6 = xlsread('analyzeV6.xls');
pureNumWholeWeek6(end+1,:) = 0;
normalizedPureNumberWholeWeek6 = mapminmax(pureNumWholeWeek6',0,1)';
sumWeeklyEffort6 = sum(normalizedPureNumberWholeWeek6(:,effortCol),2);
sumWeeklyCost6 = sum(normalizedPureNumberWholeWeek6(:,costCol),2);
productivity6 = sumWeeklyEffort6./sumWeeklyCost6;
traditionalProductivity6 = normalizedPureNumberWholeWeek6(:,13)./normalizedPureNumberWholeWeek6(:,18); 

figure('name','three versions by week')
clf;
hold on;
plot(productivity4,'-o')
plot(productivity5,'-s')
plot(productivity6,'-x')
hold off;

figure('name','v4 normal and traditional')
clf;
hold on;
plot(productivity4,'-o')
plot(traditionalProductivity4,'-x')
plot(traditionalProductivity4.^2.*productivity4,'-v')
hold off;

figure('name','v5 normal and traditional')
clf;
hold on;
plot(productivity5,'-o')
plot(traditionalProductivity5,'-x')
plot(traditionalProductivity5.^2.*productivity5,'-v')
hold off;

figure('name','v6 normal and traditional')
clf;
hold on;
plot(productivity6,'-o')
plot(traditionalProductivity6,'-x')
plot(traditionalProductivity6.^2.*productivity6,'-v')
hold off;